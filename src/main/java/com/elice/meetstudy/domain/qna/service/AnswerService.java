package com.elice.meetstudy.domain.qna.service;

import com.elice.meetstudy.domain.qna.domain.Answer;
import com.elice.meetstudy.domain.qna.domain.AnswerStatus;
import com.elice.meetstudy.domain.qna.domain.Question;
import com.elice.meetstudy.domain.qna.dto.RequestAnswerDto;
import com.elice.meetstudy.domain.qna.dto.ResponseAnswerDto;
import com.elice.meetstudy.domain.qna.exception.SingleResponseViolationException;
import com.elice.meetstudy.domain.qna.mapper.AnswerMapper;
import com.elice.meetstudy.domain.qna.repository.AnswerRepository;
import com.elice.meetstudy.domain.qna.repository.QuestionRepository;
import com.elice.meetstudy.domain.studyroom.exception.EntityNotFoundException;
import com.elice.meetstudy.domain.user.domain.User;
import com.elice.meetstudy.domain.user.repository.UserRepository;
import com.elice.meetstudy.util.EntityFinder;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
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
  private final EntityFinder entityFinder;

  /** 답변 조회 */
  public ResponseAnswerDto getAnswer(long questionId) {
    Question question =
        questionRepository.findById(questionId)
            .orElseThrow(()->new NotFoundException("질문이 존재하지 않습니다."));

      Answer answer = question.getAnswer();
      if (answer == null || answer.getId() == null) {
        throw new EntityNotFoundException("답변이 등록되지 않았습니다.");
      }
      return answerMapper.toResponseAnswerDto(answerRepository.findById(answer.getId()).get());

  }

  /** 답변 생성 */
  @Transactional
  public ResponseAnswerDto addAnswer(RequestAnswerDto requestAnswerDto, long questionId)
      throws AccessDeniedException {
    User user =
        userRepository
            .findById(entityFinder.getUser().getId())
            .orElseThrow(() -> new EntityNotFoundException("사용자가 존재하지 않습니다."));

    Question question =
        questionRepository
            .findById(questionId)
            .orElseThrow(() -> new EntityNotFoundException("질문이 존재하지 않습니다."));

    if (question.getAnswer() != null) {
      throw new SingleResponseViolationException("질문 하나에는 답변 하나만 등록할 수 있습니다.");
    }

    Answer answer = new Answer(requestAnswerDto.content());
    answer.setQuestion(question);
    question.setAnswerStatus(AnswerStatus.COMPLETED); // 질문의 답변 상태 변경
    question.setAnswer(answer);

    return answerMapper.toResponseAnswerDto(answerRepository.save(answer));
  }

  /** 답변 수정 */
  @Transactional
  public ResponseAnswerDto updateAnswer(RequestAnswerDto requestAnswerDto, long answerId)
      throws AccessDeniedException {
    User user =
        userRepository
            .findById(entityFinder.getUser().getId())
            .orElseThrow(() -> new EntityNotFoundException("사용자가 존재하지 않습니다."));

    Answer answer =
        answerRepository
            .findById(answerId)
            .orElseThrow(() -> new EntityNotFoundException("존재하는 답변이 아닙니다."));

    Answer answer1 = answer;
    answer1.update(requestAnswerDto.content());
    return answerMapper.toResponseAnswerDto(answer1);
  }

  /** 답변 삭제 */
  @Transactional
  public void deleteAnswer(long answerId) throws AccessDeniedException {
    User user =
        userRepository
            .findById(entityFinder.getUser().getId())
            .orElseThrow(() -> new EntityNotFoundException("사용자가 존재하지 않습니다."));

    Question question = questionRepository.findByAnswerId(answerId);
    question.setAnswerStatus(AnswerStatus.PENDING); // 답변 상태 수정
    question.setAnswer(null);
    answerRepository.deleteById(answerId);
  }
}
