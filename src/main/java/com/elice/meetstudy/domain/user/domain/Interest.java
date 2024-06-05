package com.elice.meetstudy.domain.user.domain;

import com.elice.meetstudy.domain.category.entity.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Interest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public static Interest createInterest(User user, Category category) {
        Interest interest = new Interest();
        interest.setUser(user);
        interest.setCategory(category);
        user.addInterest(interest); // User 클래스에 관심사를 추가하는 메소드를 호출합니다.
        return interest;
    }

    private void setCategory(Category category) {
    }

    public void setUser(User user) {} // 없으면 실행 안됨 -> 추가

}
