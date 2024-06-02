package com.elice.meetstudy.domain.category.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *  category Entity
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
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

  public Category(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public void update(Category updatedCategory) {
    if (updatedCategory.getName() != null) {
      this.name = updatedCategory.getName();
    }
    if (updatedCategory.getDescription() != null) {
      this.description = updatedCategory.getDescription();
    }
  }
}
