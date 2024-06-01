package com.elice.meetstudy.domain.qna.dto;

import com.elice.meetstudy.domain.qna.domain.QuestionCategory;

public record RequestQuestionDto(
    String title,
    String content,
    QuestionCategory questionCategory,
    boolean isSecret,
    String password
)
{}
