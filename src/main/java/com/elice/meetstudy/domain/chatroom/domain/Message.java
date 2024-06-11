package com.elice.meetstudy.domain.chatroom.domain;

import com.elice.meetstudy.domain.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
public class Message {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "message_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "chat_room_id")
  private ChatRoom chatRoom;

  @ManyToOne
  @JoinColumn(name="user_id",nullable = false)
  private User sender;

  @Column(name = "content")
  private String content;

  @Column(name = "create_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private LocalDateTime createAt;

  @Builder
  public Message(ChatRoom chatRoom, User sender, String content, LocalDateTime createAt) {
    this.chatRoom = chatRoom;
    this.sender = sender;
    this.content = content;
    this.createAt = createAt;
  }
}
