package com.elice.meetstudy.domain.chatroom.dto;

import com.elice.meetstudy.domain.chatroom.domain.Message;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OutputMessageModel {
  private Long chatRoomId;
  private String nickName;
  private String content;
  private LocalDateTime createdAt;

  @Builder
  public OutputMessageModel(Message message) {
    this.chatRoomId = message.getChatRoom().getId();
    this.nickName = message.getSender().getNickname();
    this.content = message.getContent();
    this.createdAt = message.getCreateAt();
  }

}
