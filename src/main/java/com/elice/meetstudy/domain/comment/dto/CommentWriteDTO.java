package com.elice.meetstudy.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentWriteDTO {

  @NotBlank(message = "내용을 입력해주세요.")
  private String content;
}
