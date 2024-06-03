package com.elice.meetstudy.domain.qna.service;

import com.elice.meetstudy.domain.qna.domain.Question;
import com.elice.meetstudy.domain.qna.domain.QuestionCategory;
import com.elice.meetstudy.domain.qna.dto.RequestQuestionDto;
import com.elice.meetstudy.domain.qna.dto.ResponseQuestionDto;
import com.elice.meetstudy.domain.qna.mapper.QuestionMapper;
import com.elice.meetstudy.domain.qna.repository.QuestionRepository;
import com.elice.meetstudy.domain.user.domain.UserPrinciple;
import com.elice.meetstudy.domain.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuestionService {

  private final QuestionRepository questionRepository;
  private final QuestionMapper questionMapper;
  private final UserRepository userRepository;

  public QuestionService(QuestionRepository questionRepository, QuestionMapper questionMapper,
                         UserRepository userRepository) {
    this.questionRepository = questionRepository;
    this.questionMapper = questionMapper;
    this.userRepository = userRepository;
  }

  /** 질문 리스트 전체 조회 (키워드 설정 o ) */
  @Transactional
  public ResponseEntity<List<ResponseQuestionDto>> getAllQuestionsByKeywords(
          String keyword, int page, int size, String category) {
    PageRequest pageRequest = PageRequest.of(page, size);
    List<ResponseQuestionDto> responseQuestionDtoList = new ArrayList<>();
    Page<Question> questions;

    if (category != null && !category.isEmpty()) {
      return getAllQuestionsByCategory(
              QuestionCategory.valueOf(category.toUpperCase()), pageRequest, keyword);
    } else {
      if (keyword != null && !keyword.isEmpty())
        questions = questionRepository.findAllByTitleContainingOrderByIdDesc(keyword, pageRequest);
      else questions = questionRepository.findAllByOrderByIdDesc(pageRequest);

      for (Question question : questions) {
        responseQuestionDtoList.add(questionMapper.toResponseQuestionDto(question));
      }
      return new ResponseEntity(responseQuestionDtoList, HttpStatus.OK);
    }
  }

  /** 질문 리스트 카테고리별 조회  */
  @Transactional
  public ResponseEntity<List<ResponseQuestionDto>> getAllQuestionsByCategory(
          QuestionCategory questionCategory, PageRequest pageRequest, String keyword) {
    Page<Question> questions;
    List<ResponseQuestionDto> responseQuestionDtoList = new ArrayList<>();
    if (keyword != null && !keyword.isEmpty()) {
      questions =
              questionRepository.findAllByTitleContainingAndQuestionCategoryOrderByIdDesc(
                      keyword, questionCategory, pageRequest);
    } else
      questions =
              questionRepository.findAllByQuestionCategoryOrderByIdDesc(pageRequest, questionCategory);
    for (Question question : questions) {
      responseQuestionDtoList.add(questionMapper.toResponseQuestionDto(question));
    }
    return new ResponseEntity(responseQuestionDtoList, HttpStatus.OK);
  }

  /** 질문 개별 조회 : 관리자는 다 볼 수 있게 추가 */
  @Transactional
  public ResponseEntity<?> getQuestion(long questionId, String password) {
    Optional<Question> Question = questionRepository.findById(questionId);

    if (Question.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    Question question = Question.get();

    if (question.isSecret()) { // 비밀글인 경우 : 수정
      if (password == null || !password.equals(question.getPassword()))
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    return new ResponseEntity<>(question, HttpStatus.OK);
  }

  /** 질문 생성 */
  @Transactional
  public ResponseEntity<ResponseQuestionDto> postQuestion(RequestQuestionDto requestQuestionDto) {
    Question question = questionMapper.toQuestionEntity(requestQuestionDto);
    return new ResponseEntity<>(
            questionMapper.toResponseQuestionDto(questionRepository.save(question)), HttpStatus.OK);
  }

  /** 질문 수정 : 조회 이후 */
  @Transactional
  public ResponseEntity<ResponseQuestionDto> updateQuestion(
          RequestQuestionDto re, long questionId) {
    Optional<Question> question = questionRepository.findById(questionId);
    long userId = getUserId();
    if (question.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    Question question1 = question.get();
    if (question1.getUser().getId() == userId) {
      question1.update(
              re.title(), re.content(), re.questionCategory(), re.isSecret(), re.password());
      return new ResponseEntity<>(questionMapper.toResponseQuestionDto(question1), HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
  }

  /** 질문 삭제 : 조회 이후 */
  @Transactional
  public ResponseEntity<?> deleteQuestion(long questionId) {
    Optional<Question> question = questionRepository.findById(questionId);
    long userId = getUserId();

    Question question1 = question.get();
    if(question1.getUser().getId() == userId)
      questionRepository.deleteById(questionId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @Transactional
  public long getUserId() {
    // 접근한 유저 정보 가져오는 로직
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
    String userEmail = userPrinciple.getEmail();
    return userRepository.findUserIdByEmail(userEmail);
  }
}
