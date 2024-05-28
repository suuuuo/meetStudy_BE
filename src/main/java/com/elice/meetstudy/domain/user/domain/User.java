package com.elice.meetstudy.domain.user.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.*;
import org.springframework.expression.spel.ast.NullLiteral;

/**
 * user Entity
 */

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(length = 50)
    private String nickname;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime deletedAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Interest> interests = new ArrayList<>();



    public void updateDeletedAt() {
        this.deletedAt = LocalDateTime.now();
    }

    @Builder
    public User(String email, String password, String username, String nickname,
                LocalDateTime createdAt, LocalDateTime deletedAt, Role role){
        this.email = email;
        this.password = password;
        this.username = username;
        this.nickname = nickname;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
        this.role = role;
    }
  }
