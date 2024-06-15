package com.elice.meetstudy.domain.qna.controller;

import com.elice.meetstudy.domain.qna.dto.RequestAnswerDto;
import com.elice.meetstudy.domain.qna.dto.ResponseAnswerDto;
import com.elice.meetstudy.domain.qna.service.AnswerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Tag(name = "N. 답변", description = "문의에 대한 답변 관련 API 입니다.")
public class AnswerController {

  private final AnswerService answerService;

  @Operation(summary = "답변 조회", description = "답변 조회가 가능합니다.")
  @GetMapping("/answer/public/{questionId}")
  public ResponseEntity<ResponseAnswerDto> getAnswer(@PathVariable long questionId) {
    return new ResponseEntity<>(answerService.getAnswer(questionId), HttpStatus.OK);
  }

  @Operation(summary = "답변 등록(관리자)", description = "questionId로 질문을 찾아 답변을 등록합니다."
      + " 답변이 등록되면 질문의 답변 상태가 PENDING 에서 COMPLETE 로 변경됩니다.")
  @PostMapping("/admin/answer/{questionId}")
  public ResponseEntity<ResponseAnswerDto> addAnswer(
      @RequestBody @Valid RequestAnswerDto requestAnswerDto, @PathVariable long questionId)
      throws AccessDeniedException {
    return new ResponseEntity<>(
        answerService.addAnswer(requestAnswerDto, questionId), HttpStatus.OK);
  }

  @Operation(summary = "답변 수정(관리자)", description = "answerId로 답변을 찾아 수정합니다.")
  @PutMapping("/admin/answer/{answerId}")
  public ResponseEntity<ResponseAnswerDto> updateAnswer(
      @PathVariable long answerId, @RequestBody @Valid RequestAnswerDto requestAnswerDto)
      throws AccessDeniedException {
    return new ResponseEntity<>(
        answerService.updateAnswer(requestAnswerDto, answerId), HttpStatus.OK);
  }

  @Operation(summary = "답변 삭제(관리자)", description = "answerId로 답변을 찾아 삭제합니다. "
      + "질문 상태가 COMPLETE 에서 PENDING 으로 변경됩니다")
  @DeleteMapping("/admin/answer/{answerId}")
  public ResponseEntity<ResponseAnswerDto> deleteAnswer(@PathVariable long answerId)
      throws AccessDeniedException {
    answerService.deleteAnswer(answerId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
