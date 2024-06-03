package com.elice.meetstudy.domain.qna.service;

import com.elice.meetstudy.domain.qna.domain.Answer;
import com.elice.meetstudy.domain.qna.domain.AnswerStatus;
import com.elice.meetstudy.domain.qna.domain.Question;
import com.elice.meetstudy.domain.qna.dto.RequestAnswerDto;
import com.elice.meetstudy.domain.qna.mapper.AnswerMapper;
import com.elice.meetstudy.domain.qna.repository.AnswerRepository;
import com.elice.meetstudy.domain.qna.repository.QuestionRepository;
import com.elice.meetstudy.domain.user.domain.Role;
import com.elice.meetstudy.domain.user.domain.User;
import com.elice.meetstudy.domain.user.domain.UserPrinciple;
import com.elice.meetstudy.domain.user.repository.UserRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AnswerService {

  private final AnswerRepository answerRepository;
  private final AnswerMapper answerMapper;
  private final QuestionRepository questionRepository;
  private final UserRepository userRepository;

  /** 답변 관리는 관리자만 할 수 있도록 설정 */

  /** 답변 조회 */
  @Transactional
  public ResponseEntity<?> getAnswer(long questionId) {
    Optional<Question> question = questionRepository.findById(questionId);
    if (question.isPresent()) {
      long answerId = question.get().getAnswer().getId();
      if(answerId != 0L)
      return new ResponseEntity<>(
          answerMapper.toResponseAnswerDto(answerRepository.findById(answerId).get()),
          HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  /** 답변 생성 */
  @Transactional
  public ResponseEntity<?> addAnswer(RequestAnswerDto requestAnswerDto, long questionId) {
    Optional<User> user = userRepository.findById(getUserId());
    if(user.get().getRole() == Role.ADMIN){
      Optional<Question> question = questionRepository.findById(questionId);
      if (question.isPresent()) {
        Answer answer = new Answer(requestAnswerDto.content());
        answerRepository.save(answer);
        question.get().setAnswerStatus(AnswerStatus.COMPLETED); // 질문의 답변 상태 변경
        question.get().setAnswer(answer);
        return new ResponseEntity<>(answerMapper.toResponseAnswerDto(answer), HttpStatus.OK);
      }
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }return  new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
  }

  /** 답변 수정 */
  @Transactional
  public ResponseEntity<?> updateAnswer(RequestAnswerDto requestAnswerDto, long answerId) {
    Optional<User> user = userRepository.findById(getUserId());
    if(user.get().getRole() == Role.ADMIN){
      Optional<Answer> answer = answerRepository.findById(answerId);
      if (answer.isPresent()) {
        Answer answer1 = answer.get();
        answer1.update(requestAnswerDto.content());
        return new ResponseEntity<>(answerMapper.toResponseAnswerDto(answer1), HttpStatus.OK);
      }
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
  }

  /** 답변 삭제 */
  @Transactional
  public ResponseEntity<?> deleteAnswer(long answerId) {
    Optional<User> user = userRepository.findById(getUserId());
    if(user.get().getRole() == Role.ADMIN){
      Question question = questionRepository.findByAnswerId(answerId);
      question.setAnswerStatus(AnswerStatus.PENDING); //답변 상태 수정
      question.setAnswer(null);
      answerRepository.deleteById(answerId);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
  }

  @Transactional
  public long getUserId() {
    // 접근한 유저 정보 가져오는 로직
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserPrinciple userPrinciple = (UserPrinciple)authentication.getPrincipal();
    return Long.parseLong(userPrinciple.getEmail());
  }
}
