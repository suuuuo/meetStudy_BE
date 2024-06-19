package com.elice.meetstudy.domain.qna.dto;

import com.elice.meetstudy.domain.qna.domain.AnswerStatus;
import com.elice.meetstudy.domain.qna.domain.QuestionCategory;
import com.fasterxml.jackson.annotation.JsonFormat;

public record ResponseQuestionDto(
    long id,
    String title,
    String content,
    String createdBy,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy.MM.dd HH:mm")
    String createdAt,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy.MM.dd HH:mm")
    String updatedAt,
    AnswerStatus answerStatus,
    QuestionCategory questionCategory,
    boolean isSecret
)
{}
