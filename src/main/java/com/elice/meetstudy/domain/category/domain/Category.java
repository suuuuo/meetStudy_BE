package com.elice.meetstudy.domain.category.domain;

import jakarta.persistence.*;

/**
 *  category Entity
 */

@Entity
@Table(name = "category")
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String description;

}
