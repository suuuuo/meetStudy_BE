package com.elice.meetstudy.domain.scrap.domain;

import com.elice.meetstudy.domain.category.entity.Category;
import com.elice.meetstudy.domain.post.domain.Post;
import com.elice.meetstudy.domain.user.domain.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "scrap")
public class Scrap {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "category_id", nullable = false)
  private Category board;

  @ManyToOne
  @JoinColumn(name = "post_id", nullable = false)
  private Post post;

  @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private LocalDateTime createdAt;

}
