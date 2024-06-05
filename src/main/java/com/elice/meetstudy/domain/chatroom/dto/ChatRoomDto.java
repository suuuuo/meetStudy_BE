package com.elice.meetstudy.domain.chatroom.dto;

import com.elice.meetstudy.domain.chatroom.domain.ChatRoom;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatRoomDto {
  private Long id;
  private String title;
  private Long studyRoomId;
  private String notice;

  @Builder
  public ChatRoomDto (ChatRoom chatRoom){
    this.id = chatRoom.getId();
    this.title = chatRoom.getTitle();
    this.studyRoomId = chatRoom.getStudyRoom().getId();
    this.notice = chatRoom.getNotice();
  }
}
