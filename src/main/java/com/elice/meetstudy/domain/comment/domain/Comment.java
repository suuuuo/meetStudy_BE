package com.elice.meetstudy.domain.comment.domain;

import com.elice.meetstudy.domain.post.domain.Post;
import com.elice.meetstudy.domain.user.domain.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 *  comment Entity
 */
@Entity
@Table(name = "comment")
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

  @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private LocalDateTime createdAt;

}