package com.elice.meetstudy.domain.qna.controller;

import com.elice.meetstudy.domain.qna.dto.RequestQuestionDto;
import com.elice.meetstudy.domain.qna.dto.ResponseQuestionDto;
import com.elice.meetstudy.domain.qna.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.nio.file.AccessDeniedException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
@RequestMapping("api")
@Tag(name = "M. 문의", description = "문의 게시판 관련 API 입니다.")
public class QuestionController {

  private final QuestionService questionService;

  /** 질문 전체 조회 (키워드) */
  @Operation(
      summary = "질문 조회",
      description = "키워드나 카테고리를 입력하면 해당되는 질문 조회, 입력하지 않으면 전체 질문 조회가 가능합니다.")
  @GetMapping("/question/public")
  public ResponseEntity<List<ResponseQuestionDto>> getAllQuestions(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int size,
      @RequestParam(required = false) String keyword,
      @RequestParam(required = false) String category) {
    return new ResponseEntity<>(
        questionService.getAllQuestionsByKeywords(keyword, page, size, category), HttpStatus.OK);
  }

  /** 질문 개별 조회 */
  @Operation(summary = "질문 조회", description = "질문 id를 통해 개별 조회가 가능합니다.")
  @GetMapping("/question/public/{questionId}")
  public ResponseEntity<ResponseQuestionDto> getQuestion(
      @PathVariable long questionId, @RequestParam(required = false) String password)
      throws AccessDeniedException {
    return new ResponseEntity<>(questionService.getQuestion(questionId, password), HttpStatus.OK);
  }

  /**
   * @param requestQuestionDto
   * @return responseQuestionDto 질문 생성
   */
  @Operation(summary = "질문 등록", description = "질문 등록이 가능합니다. 상태는 대기중으로 지정됩니다.")
  @PostMapping("/question")
  public ResponseEntity<ResponseQuestionDto> postQuestion(
      @Validated @RequestBody RequestQuestionDto requestQuestionDto) {
    return new ResponseEntity<>(questionService.postQuestion(requestQuestionDto), HttpStatus.OK);
  }

  /**
   * 질문 수정
   *
   * @param requestQuestionDto
   * @param questionId
   * @return
   */
  @Operation(summary = "질문 수정", description = "질문 수정이 가능합니다.")
  @PutMapping("/question/{questionId}")
  public ResponseEntity<ResponseQuestionDto> updateQuestion(
      @RequestBody @Valid RequestQuestionDto requestQuestionDto, @PathVariable long questionId)
      throws AccessDeniedException {
    return new ResponseEntity<>(
        questionService.updateQuestion(requestQuestionDto, questionId), HttpStatus.OK);
  }

  /** 질문 삭제 */
  @Operation(summary = "질문 삭제", description = "질문 삭제가 가능합니다.")
  @DeleteMapping("/question/{questionId}")
  public ResponseEntity<HttpStatus> deleteQuestion(@PathVariable long questionId) {
    questionService.deleteQuestion(questionId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
