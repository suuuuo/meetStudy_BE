package com.elice.meetstudy.domain.post.domain;

import com.elice.meetstudy.domain.user.domain.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "post_like")
public class PostLike {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "post_id", nullable = false)
  private Post post;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

}
