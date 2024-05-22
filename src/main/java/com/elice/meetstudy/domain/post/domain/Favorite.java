package com.elice.meetstudy.domain.post.domain;

import com.elice.meetstudy.domain.category.domain.Category;
import com.elice.meetstudy.domain.user.domain.User;
import jakarta.persistence.*;

/**
 *  favorite Entity (회원가입시, 회원의 관심사)
 **/

@Entity
public class Favorite {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;

}

