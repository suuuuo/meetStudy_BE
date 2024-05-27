package com.elice.meetstudy.domain.chatroom.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageDto {

  private Long chatRoomId;
  private Long userId;
  private String content;
  private String createdAt;
}
