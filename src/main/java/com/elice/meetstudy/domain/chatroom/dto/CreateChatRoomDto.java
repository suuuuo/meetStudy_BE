package com.elice.meetstudy.domain.chatroom.dto;

import com.elice.meetstudy.domain.chatroom.domain.ChatRoom;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CreateChatRoomDto {
    private Long chatRoomId;
    private String title;
    private Long studyRoomId;
    private String notice;

  @Builder
  public CreateChatRoomDto(ChatRoom chatRoom){
    this.chatRoomId = chatRoom.getId();
    this.title = chatRoom.getTitle();
    this.studyRoomId = chatRoom.getStudyRoom().getId();
    this.notice = chatRoom.getNotice();
  }

}
