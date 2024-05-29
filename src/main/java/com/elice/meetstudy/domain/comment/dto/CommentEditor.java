package com.elice.meetstudy.domain.comment.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentEditor {

  private final String content;

  public CommentEditor(String content) {
    this.content = content;
  }
}
