package com.elice.meetstudy.domain.category.entity;

import com.elice.meetstudy.domain.post.domain.Post;
import com.elice.meetstudy.domain.studyroom.entity.StudyRoom;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
