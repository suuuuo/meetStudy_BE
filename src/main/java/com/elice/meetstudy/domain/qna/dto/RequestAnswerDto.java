package com.elice.meetstudy.domain.qna.dto;

import jakarta.validation.constraints.NotBlank;

public record RequestAnswerDto(
    @NotBlank(message = "내용을 입력해주세요")
    String content
)
{}
