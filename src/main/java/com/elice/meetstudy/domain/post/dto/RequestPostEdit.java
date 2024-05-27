package com.elice.meetstudy.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RequestPostEdit {

  @NotNull
  private Long categoryId;

  @NotNull
  private Long userId;

  @NotBlank(message = "제목을 입력해주세요.")
  private String title;

  @NotBlank(message = "내용을 입력해주세요.")
  private String content;

}

