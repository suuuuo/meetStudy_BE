package com.elice.meetstudy.domain.comment.domain;

import com.elice.meetstudy.domain.comment.dto.CommentEditDTO;
import com.elice.meetstudy.domain.post.domain.Post;
import com.elice.meetstudy.domain.user.domain.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** comment Entity */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "post_id", nullable = false)
  private Post post;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(nullable = false)
  private String content;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
  }

  @Builder
  public Comment(Post post, User user, String content, Long userId) {
    this.post = post;
    this.user = user;
    this.content = content;
    if (userId != null) {
      this.user = User.builder().id(userId).build();
    }
  }

  public CommentEditDTO.CommentEditDTOBuilder toEdit() {
    return CommentEditDTO.builder().content(this.content);
  }

  public void edit(CommentEditDTO commentEdit) {
    this.content = commentEdit.getContent();
  }
}
