package com.elice.meetstudy.domain.chatroom.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
@Entity
@Getter
public class ChatRoom {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


}
