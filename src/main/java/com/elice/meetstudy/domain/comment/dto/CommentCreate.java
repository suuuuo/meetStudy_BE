package com.elice.meetstudy.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentCreate {

  @NotNull private Long userId;

  @NotNull private Long postId;

  @NotBlank(message = "내용을 입력해주세요.")
  private String content;
}
