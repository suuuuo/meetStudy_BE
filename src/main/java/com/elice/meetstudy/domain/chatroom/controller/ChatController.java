package com.elice.meetstudy.domain.chatroom.controller;

import com.elice.meetstudy.domain.chatroom.dto.ChatRoomDto;
import com.elice.meetstudy.domain.chatroom.service.ChatRoomService;
import com.elice.meetstudy.domain.studyroom.DTO.StudyRoomDTO;
import com.elice.meetstudy.domain.studyroom.service.StudyRoomService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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


}
