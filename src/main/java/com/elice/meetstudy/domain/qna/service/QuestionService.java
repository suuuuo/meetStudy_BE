package com.elice.meetstudy.domain.qna.service;

import com.elice.meetstudy.domain.calendar.domain.Calendar_detail;
import com.elice.meetstudy.domain.calendar.dto.ResponseCalendarDetail;
import com.elice.meetstudy.domain.qna.domain.Question;
import com.elice.meetstudy.domain.qna.dto.ResponseQuestionDto;
import com.elice.meetstudy.domain.qna.mapper.QuestionMapper;
import com.elice.meetstudy.domain.qna.repository.QuestionRepository;
import java.util.ArrayList;
import java.util.List;
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

    /**
     * 질문 리스트 전체 조회
     */
    @Transactional
    public List<ResponseQuestionDto> getAllQuestions(){
        List<Question> questions = questionRepository.findAllByOrderByIdDesc();
        List<ResponseQuestionDto> responseQuestionDtoList = new ArrayList<>();
        for (Question question : questions) {
            responseQuestionDtoList.add(questionMapper.toResponseQuestionDto(question));
        }
        return responseQuestionDtoList;
    }

    /**
     * 질문 리스트 카테고리별 조회
     */

    /**
     * 질문 개별 조회
     */

    /**
     * 질문 생성
     */
    @Transactional
    public ResponseQuestionDto postQuestion(Question question){
        return questionMapper.toResponseQuestionDto(questionRepository.save(question));

    }

    /**
     * 질문 수정
     */

    /**
     * 질문 삭제
     */
}
