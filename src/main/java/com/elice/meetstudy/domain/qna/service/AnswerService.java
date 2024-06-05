package com.elice.meetstudy.domain.qna.service;

import com.elice.meetstudy.domain.qna.domain.Answer;
import com.elice.meetstudy.domain.qna.domain.AnswerStatus;
import com.elice.meetstudy.domain.qna.domain.Question;
import com.elice.meetstudy.domain.qna.dto.RequestAnswerDto;
import com.elice.meetstudy.domain.qna.dto.ResponseAnswerDto;
import com.elice.meetstudy.domain.qna.mapper.AnswerMapper;
import com.elice.meetstudy.domain.qna.repository.AnswerRepository;
import com.elice.meetstudy.domain.qna.repository.QuestionRepository;
import com.elice.meetstudy.domain.studyroom.exception.EntityNotFoundException;
import com.elice.meetstudy.domain.user.domain.Role;
import com.elice.meetstudy.domain.user.domain.User;
import com.elice.meetstudy.domain.user.domain.UserPrinciple;
import com.elice.meetstudy.domain.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

@RequiredArgsConstructor
@Service
public class AnswerService {

  private final AnswerRepository answerRepository;
  private final AnswerMapper answerMapper;
  private final QuestionRepository questionRepository;
  private final UserRepository userRepository;


  /** 답변 조회 */

  public ResponseAnswerDto getAnswer(long questionId) {
    Optional<Question> question = questionRepository.findById(questionId);
    System.out.println(question.get().getId());
    if (question.isPresent()) {
      Answer answer = question.get().getAnswer();
      if(answer == null || answer.getId() == null){
        throw new EntityNotFoundException("답변이 등록되지 않았습니다.");
      }
      return answerMapper.toResponseAnswerDto(answerRepository.findById(answer.getId()).get());
    }
    throw new NotFoundException("질문이 존재하지 않습니다.");
  }

  /** 답변 생성 */
  @Transactional
  public ResponseAnswerDto addAnswer(RequestAnswerDto requestAnswerDto, long questionId)
      throws AccessDeniedException {
    Optional<User> user = userRepository.findById(getUserId());
    if(user.get().getRole() == Role.ADMIN){
      Optional<Question> question = questionRepository.findById(questionId);
      if (question.isPresent()) {
        Answer answer = new Answer(requestAnswerDto.content());
        answer.setQuestion(question.get());
        question.get().setAnswerStatus(AnswerStatus.COMPLETED); // 질문의 답변 상태 변경
        question.get().setAnswer(answer);
        return answerMapper.toResponseAnswerDto(answerRepository.save(answer));
      }
      throw new EntityNotFoundException("질문이 존재하지 않습니다.");
    }throw new AccessDeniedException("관리자만 접근 가능합니다.");
  }

  /** 답변 수정 */
  @Transactional
  public ResponseAnswerDto updateAnswer(RequestAnswerDto requestAnswerDto, long answerId)
      throws AccessDeniedException {
    Optional<User> user = userRepository.findById(getUserId());
    if(user.get().getRole() == Role.ADMIN){
      Optional<Answer> answer = answerRepository.findById(answerId);
      if (answer.isPresent()) {
        Answer answer1 = answer.get();
        answer1.update(requestAnswerDto.content());
        return answerMapper.toResponseAnswerDto(answer1);
      } throw new EntityNotFoundException("존재하는 답변이 아닙니다.");
    } throw new AccessDeniedException("관리자만 접근 가능합니다.");
  }

  /** 답변 삭제 */
  @Transactional
  public void deleteAnswer(long answerId) throws AccessDeniedException {
    Optional<User> user = userRepository.findById(getUserId());
    if(user.get().getRole() == Role.ADMIN){
      Question question = questionRepository.findByAnswerId(answerId);
      question.setAnswerStatus(AnswerStatus.PENDING); //답변 상태 수정
      question.setAnswer(null);
      answerRepository.deleteById(answerId);
      return;
    } throw new AccessDeniedException("관리자만 접근 가능합니다.");
  }

  @Transactional
  public long getUserId() {
    // 접근한 유저 정보 가져오는 로직
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserPrinciple userPrinciple = (UserPrinciple)authentication.getPrincipal();
    return Long.parseLong(userPrinciple.getUserId());
  }
}
