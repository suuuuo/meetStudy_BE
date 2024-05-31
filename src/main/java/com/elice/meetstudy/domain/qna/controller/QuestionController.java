package com.elice.meetstudy.domain.qna.controller;

import com.elice.meetstudy.domain.qna.domain.Question;
import com.elice.meetstudy.domain.qna.domain.QuestionCategory;
import com.elice.meetstudy.domain.qna.dto.RequestQuestionDto;
import com.elice.meetstudy.domain.qna.dto.ResponseQuestionDto;
import com.elice.meetstudy.domain.qna.mapper.QuestionMapper;
import com.elice.meetstudy.domain.qna.service.QuestionService;
import java.util.List;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionMapper questionMapper;

    public QuestionController(QuestionService questionService, QuestionMapper questionMapper) {
        this.questionService = questionService;
        this.questionMapper = questionMapper;
    }

    /**
     * 질문 전체 조회
     * 서비스단 분리 예정
     */
    @GetMapping("question")
    public ResponseEntity<List<ResponseQuestionDto>> getAllQuestions(){
        return ResponseEntity.ok(questionService.getAllQuestions());
    }


    /**
     *
     * @param requestQuestionDto
     * @return responseQuestionDto
     * 질문 생성
     * 서비스단 분리 예정
     */

    @PostMapping("/question")
    public ResponseEntity<?> postQuestion(
        @RequestBody RequestQuestionDto requestQuestionDto
    ){
        /**
         * 토큰 검증 로직
         */
        Question question = questionMapper.toQuestionEntity(requestQuestionDto);
        return ResponseEntity.ok(questionService.postQuestion(question));
    /**
     * 아니면   Map<String, Object> response = new HashMap<>();
     *         response.put("message", "질문 등록 권한이 없습니다.");
     *         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
     */
  }
}
