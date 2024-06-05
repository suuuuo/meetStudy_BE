package com.elice.meetstudy.domain.chatroom.dto;

import com.elice.meetstudy.domain.chatroom.domain.Message;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MessageModel {

  private Long chatRoomId;
  private String nickName;
  private String content;

  @Builder
  public MessageModel(Message message){
    this.chatRoomId = message.getChatRoom().getId();
    this.content = message.getContent();
    this.nickName = message.getSender().getNickname();
  }
}
