package com.elice.meetstudy.domain.chatroom.controller;

import com.elice.meetstudy.domain.chatroom.dto.MessageModel;
import com.elice.meetstudy.domain.chatroom.dto.OutputMessageModel;
import com.elice.meetstudy.domain.chatroom.service.ChatRoomService;
import com.elice.meetstudy.domain.chatroom.service.MessageService;
import com.elice.meetstudy.domain.studyroom.service.StudyRoomService;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

  @Autowired
  private ChatRoomService chatRoomService;
  @Autowired
  private StudyRoomService studyRoomService;
  @Autowired
  private MessageService messageService;

  /**
    * let chatMessage = {
   *   nickName : 메세지 보내는 사람의 닉네임
   *   content : 메세지내용
   *   chatRoomId : 채팅방 Id
   *   createdAt : 채팅시간
   * }
   **/
  @MessageMapping("/message/send/{chatRoomId}") //app/message/send/{chatRoomId}
  @SendTo("/{chatRoomId}")
  public OutputMessageModel sendMessage(@Payload MessageModel messageModel, @DestinationVariable Long chatRoomId) {

    return messageService.sendMessage(messageModel, chatRoomId);

  }


}
