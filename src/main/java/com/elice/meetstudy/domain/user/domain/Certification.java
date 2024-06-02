package com.elice.meetstudy.domain.user.domain;

import com.elice.meetstudy.domain.category.entity.Category;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Certification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
