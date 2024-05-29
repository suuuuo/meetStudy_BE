package com.elice.meetstudy.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostCreate {

  @NotNull private Long categoryId;

  @NotNull private Long userId;

  @NotBlank(message = "제목을 입력해주세요.")
  private String title;

  @NotBlank(message = "내용을 입력해주세요.")
  private String content;

  @Builder
  public PostCreate(Long categoryId, Long userId, String title, String content) {
    this.categoryId = categoryId;
    this.userId = userId;
    this.title = title;
    this.content = content;
  }
}
