package com.elice.meetstudy.domain.comment.dto;

import com.elice.meetstudy.domain.comment.domain.Comment;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CommentResponseDTO {
  //  private Long id;
  private final String ninkname;
  private Long postId;
  private String content;
  private LocalDateTime createdAt;

  public CommentResponseDTO(Comment comment) {
    //    this.id = comment.getId();
    this.ninkname = comment.getUser().getNickname();
    this.postId = comment.getPost().getId();
    this.content = comment.getContent();
    this.createdAt = comment.getCreatedAt();
  }
}
