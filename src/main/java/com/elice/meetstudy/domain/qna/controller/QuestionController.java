package com.elice.meetstudy.domain.qna.controller;

import com.elice.meetstudy.domain.qna.dto.RequestQuestionDto;
import com.elice.meetstudy.domain.qna.dto.ResponseQuestionDto;
import com.elice.meetstudy.domain.qna.mapper.QuestionMapper;
import com.elice.meetstudy.domain.qna.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
@Tag(name = "문의", description = "문의 게시판 관련 API 입니다.")
public class QuestionController {

  private final QuestionService questionService;

  /** 질문 전체 조회 (키워드) */
  @Operation(summary = "질문 조회",
      description = "키워드나 카테고리를 입력하면 해당되는 질문 조회, 입력하지 않으면 전체 질문 조회가 가능합니다.")
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
  @Operation(summary = "질문 조회",
      description = "질문 id를 통해 개별 조회가 가능합니다.")
  @GetMapping("question/{question_id}")
  public ResponseEntity<?> getQuestion(@PathVariable long question_id,
      @RequestParam(required = false) String password){
    return questionService.getQuestion(question_id, password);
  }

  /**
   * @param requestQuestionDto
   * @return responseQuestionDto 질문 생성
   */
  @Operation(summary = "질문 등록", description = "질문 등록이 가능합니다. 상태는 대기중으로 지정됩니다.")
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
  @Operation(summary = "질문 수정", description = "질문 수정이 가능합니다.")
  @PutMapping("/question/{question_id}")
  public ResponseEntity<?> updateQuestion(@RequestBody RequestQuestionDto requestQuestionDto,
      @PathVariable long question_id){
    return questionService.updateQuestion(requestQuestionDto, question_id);
  }

  /**
   * 질문 삭제
   */
  @Operation(summary = "질문 삭제", description = "질문 삭제가 가능합니다.")
  @DeleteMapping("/question/{question_id}")
  public ResponseEntity<?> deleteQuestion(@PathVariable long question_id){
    return questionService.deleteQuestion(question_id);
  }
}
