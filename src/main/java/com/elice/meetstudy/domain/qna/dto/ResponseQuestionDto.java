package com.elice.meetstudy.domain.qna.dto;

import com.elice.meetstudy.domain.qna.domain.AnswerStatus;
import com.elice.meetstudy.domain.qna.domain.QuestionCategory;

public record ResponseQuestionDto(
    long id,
    String title,
    String content,
    AnswerStatus answerStatus,
    QuestionCategory questionCategory,
    boolean isSecret
)
{}
