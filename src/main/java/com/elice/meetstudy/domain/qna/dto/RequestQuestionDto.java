package com.elice.meetstudy.domain.qna.dto;

import com.elice.meetstudy.domain.qna.domain.QuestionCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@PasswordCheck
public record RequestQuestionDto(
    @NotBlank(message = "제목을 입력해주세요")
    @NotNull(message = "제목을 입력해주세요")
    String title,
    @NotNull(message = "내용을 입력해주세요")
    @NotBlank(message = "내용을 입력해주세요")
    String content,
    @NotNull(message = "문의 카테고리를 지정해주세요")
    QuestionCategory questionCategory,

    @NotNull
    boolean isSecret,

    String password
)
{
    public boolean isSecret() {
        return isSecret;
    }
}
