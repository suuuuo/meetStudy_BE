package com.elice.meetstudy.domain.qna.service;

import com.elice.meetstudy.domain.qna.domain.Question;
import com.elice.meetstudy.domain.qna.domain.QuestionCategory;
import com.elice.meetstudy.domain.qna.dto.RequestQuestionDto;
import com.elice.meetstudy.domain.qna.dto.ResponseQuestionDto;
import com.elice.meetstudy.domain.qna.mapper.QuestionMapper;
import com.elice.meetstudy.domain.qna.repository.QuestionRepository;
import com.elice.meetstudy.domain.user.repository.UserRepository;
import com.elice.meetstudy.util.EntityFinder;
import jakarta.persistence.EntityNotFoundException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

@RequiredArgsConstructor
@Service
public class QuestionService {

  private final QuestionRepository questionRepository;
  private final QuestionMapper questionMapper;
  private final UserRepository userRepository;
  private final EntityFinder entityFinder;

  /** 질문 리스트 전체 조회 (키워드 설정 o ) */
  @Transactional
  public List<ResponseQuestionDto> getAllQuestionsByKeywords(
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
      return responseQuestionDtoList;
    }
  }

  /** 질문 리스트 카테고리별 조회 */
  @Transactional
  public List<ResponseQuestionDto> getAllQuestionsByCategory(
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
    return responseQuestionDtoList;
  }

  @Transactional
  public ResponseQuestionDto getQuestion(long questionId, String password)
      throws AccessDeniedException {
    Optional<Question> Question = questionRepository.findById(questionId);

    if (Question.isEmpty()) throw new EntityNotFoundException();
    Question question = Question.get();

    if (question.isSecret()) { // 비밀글인 경우 : 수정
      if (password == null || !password.equals(question.getPassword()))
        throw new AccessDeniedException(null);
    }
    return questionMapper.toResponseQuestionDto(question);
  }

  /** 질문 생성 */
  @Transactional
  public ResponseQuestionDto postQuestion(RequestQuestionDto requestQuestionDto) {
    Question question = questionMapper.toQuestionEntity(requestQuestionDto);
    //        question.setUser(userRepository.findById(getUserId()).get());
    question.setUser(userRepository.findById(entityFinder.getUser().getId()).get());

    return questionMapper.toResponseQuestionDto(questionRepository.save(question));
  }

  /** 질문 수정 : 조회 이후 */
  @Transactional
  public ResponseQuestionDto updateQuestion(RequestQuestionDto re, long questionId)
      throws AccessDeniedException {
    Optional<Question> question = questionRepository.findById(questionId);
    //        long userId = getUserId();
    Long userId = entityFinder.getUser().getId();
    if (question.isEmpty()) throw new NotFoundException(null);

    Question question1 = question.get();
    if (question1.getUser().getId() == userId) {
      question1.update(
          re.title(), re.content(), re.questionCategory(), re.isSecret(), re.password());
      return questionMapper.toResponseQuestionDto(question1);
    }
    throw new AccessDeniedException("작성한 사람만 수정할 수 있습니다.");
  }

  /** 질문 삭제 : 조회 이후 */
  @Transactional
  public void deleteQuestion(long questionId) {
    Optional<Question> question = questionRepository.findById(questionId);
    //        long userId = getUserId();
    Long userId = entityFinder.getUser().getId();

    if (question.get().getUser().getId() == userId) questionRepository.deleteById(questionId);
  }

  //    @Transactional
  //    public long getUserId() {
  //        // 접근한 유저 정보 가져오는 로직
  //        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
  //        UserPrinciple userPrinciple = (UserPrinciple)authentication.getPrincipal();
  //        return Long.parseLong(userPrinciple.getUserId());
  //    }
}
