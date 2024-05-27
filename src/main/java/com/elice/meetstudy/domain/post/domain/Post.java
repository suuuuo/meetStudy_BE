package com.elice.meetstudy.domain.post.domain;

import com.elice.meetstudy.domain.category.entity.Category;
import com.elice.meetstudy.domain.post.dto.PostEditor;
import com.elice.meetstudy.domain.post.dto.RequestPostEdit;
import com.elice.meetstudy.domain.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *  post Entity
 */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id")
  private Category category;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(nullable = false)
  private String title;

  //@Lob
  @Column(nullable = false)
  private String content;

  @Column(nullable = false)
  private Long hit = 0L;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
  }


  @Builder
  public Post(Category category, User user, String title, String content) {
    this.category = category;
    this.user = user;
    this.title = title;
    this.content = content;
  }

  public PostEditor.PostEditorBuilder toEditor() {
    return PostEditor.builder()
        .title(this.title)
        .content(this.content);
  }

  public void edit(PostEditor postEditor) {
    this.title = postEditor.getTitle();
    this.content = postEditor.getContent();
  }


}
