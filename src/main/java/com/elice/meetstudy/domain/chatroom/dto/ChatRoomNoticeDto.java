package com.elice.meetstudy.domain.chatroom.dto;

import com.elice.meetstudy.domain.chatroom.domain.ChatRoom;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatRoomNoticeDto {

  private Long id;
  private Long studyRoomId;
  private String notice;

  @Builder
  public ChatRoomNoticeDto (ChatRoom chatRoom){
    this.id = chatRoom.getId();
    this.studyRoomId = chatRoom.getStudyRoom().getId();
    this.notice = chatRoom.getNotice();
  }
}
