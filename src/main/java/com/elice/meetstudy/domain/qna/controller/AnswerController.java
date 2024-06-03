package com.elice.meetstudy.domain.qna.controller;

import com.elice.meetstudy.domain.qna.dto.RequestAnswerDto;
import com.elice.meetstudy.domain.qna.dto.ResponseAnswerDto;
import com.elice.meetstudy.domain.qna.service.AnswerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.nio.file.AccessDeniedException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.config.RepositoryNameSpaceHandler;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/answer")
@Tag(name = "답변", description = "문의에 대한 답변 관련 API 입니다.")
public class AnswerController {

  private final AnswerService answerService;

  @Operation(summary = "답변 조회", description = "답변 조회가 가능합니다.")
  @GetMapping("")
  public ResponseEntity<ResponseAnswerDto> getAnswer(@RequestParam long questionId) {
    return new ResponseEntity<>(answerService.getAnswer(questionId), HttpStatus.OK);
  }

  @Operation(summary = "답변 등록",
      description = "답변 등록이 가능합니다. 답변이 등록되면 질문의 상태가 답변 완료로 수정됩니다.")
  @PostMapping("/{questionId}")
  public ResponseEntity<ResponseAnswerDto> addAnswer(
      @RequestBody RequestAnswerDto requestAnswerDto, @PathVariable long questionId)
      throws AccessDeniedException {
    return new ResponseEntity<>(answerService.addAnswer(requestAnswerDto, questionId), HttpStatus.OK);
  }

  @Operation(summary = "답변 수정", description = "답변 수정이 가능합니다.")
  @PutMapping("/{answerId}")
  public ResponseEntity<ResponseAnswerDto> updateAnswer(
      @PathVariable long answerId, @RequestBody RequestAnswerDto requestAnswerDto)
      throws AccessDeniedException {
    return new ResponseEntity<>(answerService.updateAnswer(requestAnswerDto, answerId), HttpStatus.OK);
  }

  @Operation(summary = "답변 삭제", description = "답변 삭제가 가능합니다. 질문 상태가 변경됩니다")
  @DeleteMapping("/{answerId}")
  public ResponseEntity<ResponseAnswerDto> deleteAnswer(@PathVariable long answerId)
      throws AccessDeniedException {
     answerService.deleteAnswer(answerId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
