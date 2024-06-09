package com.elice.meetstudy.domain.chatroom.dto;

import com.elice.meetstudy.domain.chatroom.domain.ChatRoom;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CreateChatRoomDto {

    private String title;
    private Long studyRoomId;
    private Long chatAdminId;
    private String notice;

  @Builder
  public CreateChatRoomDto(ChatRoom chatRoom){
    this.title = chatRoom.getTitle();
    this.studyRoomId = chatRoom.getStudyRoom().getId();
    this.notice = chatRoom.getNotice();
    this.chatAdminId = chatRoom.getChatAdmin().getId();
  }

}
