package com.elice.meetstudy.domain.qna.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestAnswerDto(
    @NotBlank(message = "내용을 입력해주세요")
    @NotNull
    String content
)
{}
