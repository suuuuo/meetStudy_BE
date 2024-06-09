package com.elice.meetstudy.domain.chatroom.controller;

import com.elice.meetstudy.domain.chatroom.dto.MessageModel;
import com.elice.meetstudy.domain.chatroom.dto.OutputMessageModel;
import com.elice.meetstudy.domain.chatroom.service.ChatRoomService;
import com.elice.meetstudy.domain.chatroom.service.MessageService;
import com.elice.meetstudy.domain.studyroom.service.StudyRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "L.메세지", description = "메세지 관련 API 입니다.")
public class ChatController {

  @Autowired
  private ChatRoomService chatRoomService;
  @Autowired
  private StudyRoomService studyRoomService;
  @Autowired
  private MessageService messageService;

  /**
    * let chatMessage = {
   *   userId : 메세지 보내는 사람의 닉네임
   *   content : 메세지내용
   *   chatRoomId : 채팅방 Id
   *   createdAt : 채팅시간
   * }
   **/
  @MessageMapping("/{chatRoomId}") //app/message/send/{chatRoomId}
  @SendTo("/room/{chatRoomId}")
  public OutputMessageModel sendMessage(@Payload MessageModel messageModel, @DestinationVariable Long chatRoomId) {

    return messageService.sendMessage(messageModel, chatRoomId);
  }
  //입장메세지
  @MessageMapping("/enter/{chatRoomId}")
  @SendTo("/room/{chatRoomId}")
  public OutputMessageModel enterMessage(@DestinationVariable Long chatRoomId){

    return messageService.enterMessage(chatRoomId);
  }

  //퇴장메세지
  @MessageMapping("/exit/{chatRoomId}")
  @SendTo("/room/{chatRoomId}")
  public OutputMessageModel exitMessage(@DestinationVariable Long chatRoomId){

    return messageService.exitMessage(chatRoomId);
  }

  @Operation(summary = "메세지 조회", description = "채팅룸id를 받아와서 메세지를 page로 조회합니다.")
  @GetMapping("/chat/{chatRoomId}")
  public ResponseEntity<Page<OutputMessageModel>> chatList(@PathVariable Long chatRoomId){
    return ResponseEntity.ok(messageService.messages(chatRoomId));
  }


}
