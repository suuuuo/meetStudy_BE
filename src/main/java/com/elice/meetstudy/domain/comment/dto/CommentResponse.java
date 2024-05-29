package com.elice.meetstudy.domain.comment.dto;

import com.elice.meetstudy.domain.comment.domain.Comment;
import java.time.LocalDateTime;
import lombok.Data;

@Data
/** 정확히 뭐가 붙어야하는걸까? Getter Setter equals() hashCode() toString() */
public class CommentResponse {
  private Long id;
  private Long userId;
  private Long postId;
  private String content;
  private LocalDateTime createdAt;

  public CommentResponse(Comment comment) {
    this.id = comment.getId();
    this.userId = comment.getUser().getId();
    this.postId = comment.getPost().getId();
    this.content = comment.getContent();
    this.createdAt = comment.getCreatedAt();
  }
}
