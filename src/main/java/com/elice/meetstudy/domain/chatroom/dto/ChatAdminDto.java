package com.elice.meetstudy.domain.chatroom.dto;

import com.elice.meetstudy.domain.chatroom.domain.ChatRoom;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatAdminDto {
  private Long id;
  private String nickname;

  @Builder
  public ChatAdminDto (ChatRoom chatRoom){
    this.id = chatRoom.getId();
    this.nickname = chatRoom.getChatAdmin().getNickname();
  }

}
