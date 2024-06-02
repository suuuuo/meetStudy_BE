package com.elice.meetstudy.domain.user.domain;

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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Interest(User user, Category category) {
        this.user = user;
        this.category = category;
    }

//    public static Interest addInterest(User user, Category category) {
//        Interest interest = new Interest();
//        interest.setUser(user);
//        interest.setCategory(category);
//        // 필요한 필드들을 설정합니다.
//        return interest;
//    }
}
