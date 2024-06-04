package com.elice.meetstudy.domain.scrap.domain;

import com.elice.meetstudy.domain.category.entity.Category;
import com.elice.meetstudy.domain.post.domain.Post;
import com.elice.meetstudy.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Table(name = "scrap")
public class Scrap {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "category_id", nullable = true) // 게시판 또는 게시글을 스크랩 할 수 있음. = null 허용
  private Category category;

  @ManyToOne
  @JoinColumn(name = "post_id", nullable = true) // 게시판 또는 게시글을 스크랩 할 수 있음. = null 허용
  private Post post;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
  }

  @Builder
  public Scrap(User user, Category category, Long userId, Post post) {
    this.user = user;
    this.category = category;
    this.post = post;
    if (userId != null) {
      this.user = User.builder().id(userId).build();
    }
  }
}
