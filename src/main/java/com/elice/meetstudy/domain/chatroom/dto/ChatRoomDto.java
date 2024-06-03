package com.elice.meetstudy.domain.chatroom.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatRoomDto {
  private Long id;
  private String notice;
}
