package com.elice.meetstudy.domain.user.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

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

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String username;

    private String nickname;

    @Column(name = "join_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime joinAt;

    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime deletedAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;


    public void updateDeletedAt() {
        this.deletedAt = LocalDateTime.now();
    }

    @Builder
    public User(String email, String password, String username, String nickname,
                LocalDateTime joinAt, LocalDateTime deletedAt, Role role){
        this.email = email;
        this.password = password;
        this.username = username;
        this.nickname = nickname;
        this.joinAt = joinAt;
        this.deletedAt = deletedAt;
        this.role = role;
    }
  }
