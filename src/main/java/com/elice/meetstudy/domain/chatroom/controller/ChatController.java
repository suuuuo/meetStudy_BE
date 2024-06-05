package com.elice.meetstudy.domain.chatroom.controller;

import com.elice.meetstudy.domain.chatroom.dto.MessageModel;
import com.elice.meetstudy.domain.chatroom.dto.OutputMessageModel;
import com.elice.meetstudy.domain.chatroom.service.ChatRoomService;
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
  public OutputMessageModel sendMessage(@Payload MessageModel messageModel, @DestinationVariable Long chatRoomId){
    final String time = new SimpleDateFormat("HH:mm").format(new Date());
    OutputMessageModel outputMessageModel = new OutputMessageModel(chatRoomId,
        messageModel.getNickName(), messageModel.getContent(),
        time);

    return outputMessageModel;
  }

  //채팅방 생성
//  @PostMapping("/api/v1/{id}/chatroom/")
//  public ResponseEntity<ChatRoomDto> chatRoomDtoResponseEntity(@PathVariable Long id){
//    studyRoomService.studyRoomService.getStudyRoomById(id).get();
//  }

  //채팅방 삭제

  //채팅방 메세지 조회
//  @GetMapping("/api/v1/chatroom/{id}")
//  public ResponseEntity<Page<MessageDto>> chatMessage(@PathVariable Long id){
//    Page<MessageDto> messages = chatRoomService.messages(id);
//    return ResponseEntity.ok(messages);
//  }


}
