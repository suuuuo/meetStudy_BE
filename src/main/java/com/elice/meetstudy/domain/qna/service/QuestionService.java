package com.elice.meetstudy.domain.qna.service;

import com.elice.meetstudy.domain.qna.domain.Question;
import com.elice.meetstudy.domain.qna.domain.QuestionCategory;
import com.elice.meetstudy.domain.qna.dto.RequestQuestionDto;
import com.elice.meetstudy.domain.qna.dto.ResponseQuestionDto;
import com.elice.meetstudy.domain.qna.mapper.QuestionMapper;
import com.elice.meetstudy.domain.qna.repository.QuestionRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuestionService {

  private final QuestionRepository questionRepository;
  private final QuestionMapper questionMapper;

  public QuestionService(QuestionRepository questionRepository, QuestionMapper questionMapper) {
    this.questionRepository = questionRepository;
    this.questionMapper = questionMapper;
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
    } else{
      if (keyword != null && !keyword.isEmpty())
        questions = questionRepository.findAllByTitleContainingOrderByIdDesc(keyword, pageRequest);
      else questions = questionRepository.findAllByOrderByIdDesc(pageRequest);

      for (Question question : questions) {
        responseQuestionDtoList.add(questionMapper.toResponseQuestionDto(question));
      }
      return new ResponseEntity(responseQuestionDtoList, HttpStatus.OK);
    }
  }

  /** 질문 리스트 카테고리별 조회 */
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
    } return new ResponseEntity(responseQuestionDtoList, HttpStatus.OK);
  }

  /** 질문 개별 조회 */
  @Transactional
  public ResponseEntity<?> getQuestion(long questionId){
    return new ResponseEntity<>(questionRepository.findById(questionId), HttpStatus.OK);
  }

  /** 질문 생성 */
  @Transactional
  public ResponseEntity<ResponseQuestionDto> postQuestion(RequestQuestionDto requestQuestionDto) {
    Question question = questionMapper.toQuestionEntity(requestQuestionDto);
    return new ResponseEntity<>(
        questionMapper.toResponseQuestionDto(questionRepository.save(question)), HttpStatus.OK);
  }

  /** 질문 수정 */
  @Transactional
  public ResponseEntity<ResponseQuestionDto> updateQuestion(
      RequestQuestionDto re, long questionId) {
    Optional<Question> question = questionRepository.findById(questionId);
    if (question.isPresent()) {
      Question question1 = question.get();
     question1.update(re.title(), re.content(), re.questionCategory(), re.isSecret(), re.password());
      return new ResponseEntity<>(questionMapper.toResponseQuestionDto(question1), HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  /** 질문 삭제 */
  @Transactional
  public ResponseEntity<?> deleteQuestion(long questionId){
    questionRepository.deleteById(questionId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
