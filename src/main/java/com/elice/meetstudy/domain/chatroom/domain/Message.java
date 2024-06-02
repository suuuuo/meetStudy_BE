package com.elice.meetstudy.domain.chatroom.domain;

import com.elice.meetstudy.domain.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Getter;

@Entity
@Getter
public class Message {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  //@OneToMany
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Column(name = "content")
  private String content;

  @Column(name = "create_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private LocalDateTime create_at;

}
