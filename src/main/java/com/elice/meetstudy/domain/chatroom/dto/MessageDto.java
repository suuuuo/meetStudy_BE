package com.elice.meetstudy.domain.chatroom.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageDto {

  private Long chatRoomId;
  private String nickName;
  private String content;
  private String createdAt;
}
