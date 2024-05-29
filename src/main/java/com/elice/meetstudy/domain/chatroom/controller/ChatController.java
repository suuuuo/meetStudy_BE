package com.elice.meetstudy.domain.chatroom.controller;

import com.elice.meetstudy.domain.chatroom.dto.ChatRoomDto;
import com.elice.meetstudy.domain.chatroom.dto.MessageDto;
import com.elice.meetstudy.domain.chatroom.service.ChatRoomService;
import com.elice.meetstudy.domain.studyroom.DTO.StudyRoomDTO;
import com.elice.meetstudy.domain.studyroom.service.StudyRoomService;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

  @Autowired
  private ChatRoomService chatRoomService;
  @Autowired
  private StudyRoomService studyRoomService;
  //채팅방 생성
//  @PostMapping("/api/v1/{id}/chatroom/")
//  public ResponseEntity<ChatRoomDto> chatRoomDtoResponseEntity(@PathVariable Long id){
//    studyRoomService.studyRoomService.getStudyRoomById(id).get();
//  }

  //채팅방 삭제

  //채팅방 메세지 조회
  @GetMapping("/api/v1/chatroom/{id}")
  public ResponseEntity<Page<MessageDto>> chatMessage(@PathVariable Long id){
    Page<MessageDto> messages = chatRoomService.messages(id);
    return ResponseEntity.ok(messages);
  }


}
