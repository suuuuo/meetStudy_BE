package com.elice.meetstudy.domain.chatroom.controller;

import com.elice.meetstudy.domain.chatroom.dto.ChatRoomDto;
import com.elice.meetstudy.domain.chatroom.dto.CreateChatRoomDto;
import com.elice.meetstudy.domain.chatroom.service.ChatRoomService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "K. 채팅룸")
public class ChatRoomController {

  @Autowired private ChatRoomService chatRoomService;

  // 채팅방 생성
  @PostMapping("/chatroom/add")
  public ResponseEntity<CreateChatRoomDto> createChatRoom(
      @RequestBody CreateChatRoomDto requestChatRoomDto) {
    return ResponseEntity.ok(chatRoomService.createchatRoom(requestChatRoomDto));
  }

  // 채팅방 id로 조회
  @GetMapping("/chatroom/{chatRoomId}")
  public ResponseEntity<ChatRoomDto> chatRoomDto(@PathVariable Long chatRoomId) {
    return ResponseEntity.ok(chatRoomService.findByChatRoomId(chatRoomId));
  }

  // 채팅방 삭제
  @DeleteMapping("/chatroom/{chatRoomId}")
  public ResponseEntity<Void> deleteChatroom(@PathVariable Long chatRoomId) {
    chatRoomService.deleteChatRoom(chatRoomId);
    return ResponseEntity.noContent().build();
  }

  // 스터디룸id로 채팅방 리스트 불러오기
  @GetMapping("studyroom/{studyRoomId}/chatroom")
  public ResponseEntity<List<ChatRoomDto>> chatRoomList(@PathVariable Long studyRoomId) {
    return ResponseEntity.ok(chatRoomService.chatRoomList(studyRoomId));
  }

  // 공지사항 등록
  @PostMapping("/notice")
  public ResponseEntity<ChatRoomDto> createNotice(@RequestBody ChatRoomDto chatRoomDto) {
    return ResponseEntity.ok(chatRoomService.createNotice(chatRoomDto));
  }
}
