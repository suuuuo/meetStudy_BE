package com.elice.meetstudy.domain.user.domain;

import com.elice.meetstudy.domain.category.entity.Category;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Interest {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;

  public Interest(User user, Category category) {}

  public void setUser(User user) {} // 없으면 실행 안됨 -> 추가
}
