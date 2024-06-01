package com.elice.meetstudy.domain.qna.controller;

import com.elice.meetstudy.domain.qna.dto.RequestQuestionDto;
import com.elice.meetstudy.domain.qna.dto.ResponseQuestionDto;
import com.elice.meetstudy.domain.qna.mapper.QuestionMapper;
import com.elice.meetstudy.domain.qna.service.QuestionService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class QuestionController {

  private final QuestionService questionService;

  public QuestionController(QuestionService questionService, QuestionMapper questionMapper) {
    this.questionService = questionService;
  }

  /** 질문 전체 조회 (키워드) */
  @GetMapping("question")
  public ResponseEntity<List<ResponseQuestionDto>> getAllQuestions(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int size,
      @RequestParam(required = false) String keyword,
      @RequestParam(required = false) String category) {
    return questionService.getAllQuestionsByKeywords(keyword, page, size, category);
  }

  /**
   * 질문 개별 조회
   */
  @GetMapping("question/{question_id}")
  public ResponseEntity<?> getQuestion(@PathVariable long question_id,
      @RequestParam(required = false) String password){
    return questionService.getQuestion(question_id, password);
  }

  /**
   * @param requestQuestionDto
   * @return responseQuestionDto 질문 생성
   */
  @PostMapping("/question")
  public ResponseEntity<?> postQuestion(@RequestBody RequestQuestionDto requestQuestionDto) {
    return questionService.postQuestion(requestQuestionDto);
  }

  /**
   * 질문 수정
   * @param requestQuestionDto
   * @param question_id
   * @return
   */
  @PutMapping("/question/{question_id}")
  public ResponseEntity<?> updateQuestion(@RequestBody RequestQuestionDto requestQuestionDto,
      @PathVariable long question_id){
    return questionService.updateQuestion(requestQuestionDto, question_id);
  }

  /**
   * 질문 삭제
   */
  @DeleteMapping("/question/{question_id}")
  public ResponseEntity<?> deleteQuestion(@PathVariable long question_id){
    return questionService.deleteQuestion(question_id);
  }
}
