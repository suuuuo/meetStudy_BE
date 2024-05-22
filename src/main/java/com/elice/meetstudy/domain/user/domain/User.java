package com.elice.meetstudy.domain.user.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.springframework.context.annotation.Role;

/**
 * user Entity
 */

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(nullable = false)
//    private String email;
//
//    @Column(nullable = false)
//    private String password;
//
//    @Column(nullable = false)
//    private String username;
//
//    private String nickname;
//
//    @Column(name = "join_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
//    private LocalDateTime joinAt;
//
//    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
//    private LocalDateTime deletedAt;
//
//    @Column(nullable = false)
//    @Enumerated(EnumType.STRING)
//    private Role role;

  }
