package com.elice.meetstudy.domain.chatroom.dto;

import com.elice.meetstudy.domain.chatroom.domain.Message;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OutputMessageModel {

  private String nickName;
  private String content;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy.MM.dd HH:mm")
  private LocalDateTime createdAt;

  @Builder
  public OutputMessageModel(Message message) {
    this.nickName = message.getSender().getNickname();
    this.content = message.getContent();
    this.createdAt = message.getCreateAt();
  }
}
