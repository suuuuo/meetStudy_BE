package com.elice.meetstudy.domain.category.entity;

import jakarta.persistence.*;

import java.util.ArrayList;

/**
 *  category Entity
 */

@Entity
@Table(name = "category")
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  @Column(nullable = false)
  private String description;

//  @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
//  private List<Post> posts = new ArrayList<>();

}
