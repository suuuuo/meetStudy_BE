package com.elice.meetstudy.domain.chatroom.domain;

import com.elice.meetstudy.domain.chatroom.dto.OutputMessageModel;
import com.elice.meetstudy.domain.user.domain.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
public class Message {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "message_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "chat_room_id")
  private ChatRoom chatRoom;

  @ManyToOne
  @JoinColumn(name="user_id",nullable = false)
  private User sender;

  @Column(name = "content")
  private String content;

  @Column(name = "create_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private LocalDateTime createAt;



}
