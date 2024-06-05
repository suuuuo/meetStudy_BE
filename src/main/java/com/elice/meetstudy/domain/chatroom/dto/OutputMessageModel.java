package com.elice.meetstudy.domain.chatroom.dto;

import com.elice.meetstudy.domain.chatroom.domain.Message;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OutputMessageModel {
  private Long chatRoomId;
  private String nickName;
  private String content;
  private String createdAt;

  public OutputMessageModel(Long chatRoomId, String nickName, String content, String createdAt) {
    this.chatRoomId = chatRoomId;
    this.nickName = nickName;
    this.content = content;
    this.createdAt = createdAt;
  }

}
