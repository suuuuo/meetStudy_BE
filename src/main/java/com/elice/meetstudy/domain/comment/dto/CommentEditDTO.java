package com.elice.meetstudy.domain.comment.dto;

import lombok.Builder;
import lombok.Data;

/** 댓글 수정시 사용 */
@Data
public class CommentEditDTO {

  private final String content;

  @Builder
  public CommentEditDTO(String content) {
    this.content = content;
  }
}
