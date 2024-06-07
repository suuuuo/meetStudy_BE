package com.elice.meetstudy.domain.user.domain;

import com.elice.meetstudy.domain.category.entity.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Interest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public static Interest createInterest(Category category) {
        Interest interest = new Interest();
        interest.setCategory(category);
        return interest;
    }
}