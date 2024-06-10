package com.elice.meetstudy.domain.comment.dto;

import com.elice.meetstudy.domain.comment.domain.Comment;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CommentResponseDTO {

  private final String ninkname;
  private Long postId;
  private Long commentId;
  private String content;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy.MM.dd HH:mm")
  private LocalDateTime createdAt;

  public CommentResponseDTO(Comment comment) {
    this.ninkname = comment.getUser().getNickname();
    this.postId = comment.getPost().getId();
    this.commentId = comment.getId();
    this.content = comment.getContent();
    this.createdAt = comment.getCreatedAt();
  }
}
