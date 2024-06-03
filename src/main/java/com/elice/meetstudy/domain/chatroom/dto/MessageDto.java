package com.elice.meetstudy.domain.chatroom.dto;

import com.elice.meetstudy.domain.chatroom.domain.Message;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageDto {

  public MessageDto(Long chatRoomId) {
    this.chatRoomId = chatRoomId;
  }

  private Long chatRoomId;
  private String nickName;
  private String content;
  private String createdAt;

  public MessageDto(Message message){
      this.chatRoomId = message.getChatRoom().getId();
      this.nickName = message.getUser().getNickname();
      this.content = message.getContent();
      this.createdAt = message.getCreateAt().toString();
  }
}
