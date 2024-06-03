package com.elice.meetstudy.domain.qna.controller;

import com.elice.meetstudy.domain.qna.dto.RequestAnswerDto;
import com.elice.meetstudy.domain.qna.service.AnswerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/api/v1")
@Tag(name = "답변", description = "문의에 대한 답변 관련 API 입니다.")
public class AnswerController {
  private final AnswerService answerService;

  public AnswerController(AnswerService answerService) {
    this.answerService = answerService;
  }

  @Operation(summary = "답변 조회", description = "답변 조회가 가능합니다.")
  @GetMapping("/answer")
  public ResponseEntity<?> getAnswer(@RequestParam long questionId) {
    return answerService.getAnswer(questionId);
  }

  @Operation(summary = "답변 등록",
      description = "답변 등록이 가능합니다. 답변이 등록되면 질문의 상태가 답변 완료로 수정됩니다.")
  @PostMapping("/answer")
  public ResponseEntity<?> addAnswer(
      @RequestParam long questionId, @RequestBody RequestAnswerDto requestAnswerDto) {
    return answerService.addAnswer(requestAnswerDto, questionId);
  }

  @Operation(summary = "답변 수정", description = "답변 수정이 가능합니다.")
  @PutMapping("/answer/{answer_id}")
  public ResponseEntity<?> updateAnswer(
      @PathVariable long answer_id, @RequestBody RequestAnswerDto requestAnswerDto) {
    return answerService.updateAnswer(requestAnswerDto, answer_id);
  }

  @Operation(summary = "답변 삭제", description = "답변 삭제가 가능합니다. 질문 상태가 변경됩니다")
  @DeleteMapping("/answer/{answer_id}")
  public ResponseEntity<?> deleteAnswer(@PathVariable long answer_id) {
    return answerService.deleteAnswer(answer_id);
  }
}
