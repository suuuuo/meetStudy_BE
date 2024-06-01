package com.elice.meetstudy.domain.qna.controller;

import com.elice.meetstudy.domain.qna.dto.RequestAnswerDto;
import com.elice.meetstudy.domain.qna.service.AnswerService;
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
public class AnswerController {
  private final AnswerService answerService;

  public AnswerController(AnswerService answerService) {
    this.answerService = answerService;
  }

  @GetMapping("/answer")
  public ResponseEntity<?> getAnswer(@RequestParam long questionId) {
    return answerService.getAnswer(questionId);
  }

  @PostMapping("/answer")
  public ResponseEntity<?> addAnswer(
      @RequestParam long questionId, @RequestBody RequestAnswerDto requestAnswerDto) {
    return answerService.addAnswer(requestAnswerDto, questionId);
  }

  @PutMapping("/answer/{answer_id}")
  public ResponseEntity<?> updateAnswer(
      @PathVariable long answer_id, @RequestBody RequestAnswerDto requestAnswerDto) {
    return answerService.updateAnswer(requestAnswerDto, answer_id);
  }

  @DeleteMapping("/answer/{answer_id}")
  public ResponseEntity<?> deleteAnswer(@PathVariable long answer_id) {
    return answerService.deleteAnswer(answer_id);
  }
}
