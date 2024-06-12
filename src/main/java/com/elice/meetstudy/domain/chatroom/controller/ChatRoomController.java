package com.elice.meetstudy.domain.chatroom.controller;

import com.elice.meetstudy.domain.chatroom.dto.ChatAdminDto;
import com.elice.meetstudy.domain.chatroom.dto.ChatRoomDto;
import com.elice.meetstudy.domain.chatroom.dto.ChatRoomNoticeDto;
import com.elice.meetstudy.domain.chatroom.dto.CreateChatRoomDto;
import com.elice.meetstudy.domain.chatroom.service.ChatRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "K. 채팅방", description = "채팅방 관련 API 입니다.")
@RequiredArgsConstructor
public class ChatRoomController {

  @Autowired private final ChatRoomService chatRoomService;

  @Operation(summary = "채팅방 생성", description = "스터디룸id를 받아와서 채팅방을 생성합니다.")
  @PostMapping("/chatroom/add")
  public ResponseEntity<CreateChatRoomDto> createChatRoom(
      @RequestBody CreateChatRoomDto requestChatRoomDto) {
    return ResponseEntity.ok(chatRoomService.createchatRoom(requestChatRoomDto));
  }

  @Operation(summary = "채팅방 조회", description = "채팅방 정보를 조회합니다.")
  @GetMapping("/chatroom/{chatRoomId}")
  public ResponseEntity<ChatRoomDto> chatRoomDto(@PathVariable Long chatRoomId) {
    return ResponseEntity.ok(chatRoomService.findByChatRoomId(chatRoomId));
  }

  @Operation(summary = "채팅방 삭제", description = "채팅방을 삭제합니다.")
  @DeleteMapping("/chatroom/{chatRoomId}")
  public ResponseEntity<Void> deleteChatroom(@PathVariable Long chatRoomId) {
    chatRoomService.deleteChatRoom(chatRoomId);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "스터디룸에 해당하는 채팅방 리스트 불러오기", description = "스터디룸id를 받아서 해당하는 모든 채팅방을 조회합니다.")
  @GetMapping("/studyrooms/{studyRoomId}/chatroom")
  public ResponseEntity<List<ChatRoomDto>> chatRoomList(@PathVariable Long studyRoomId) {
    return ResponseEntity.ok(chatRoomService.chatRoomList(studyRoomId));
  }

  @Operation(summary = "공지사항 수정", description = "공지사항을 수정힙니다.")
  @PutMapping("/chatroom/notice")
  public ResponseEntity<ChatRoomNoticeDto> createNotice(@RequestBody ChatRoomNoticeDto chatRoomDto) {
    return ResponseEntity.ok(chatRoomService.createNotice(chatRoomDto));
  }

  @Operation(summary = "채팅방의 방장 변경", description = "채팅방 방장을 변경합니다.")
  @PutMapping("/chatroom/chatAdmin")
  public ResponseEntity<ChatAdminDto> changeChatAdmin(@RequestBody ChatAdminDto chatAdminDto){
    return ResponseEntity.ok(chatRoomService.changeChatAdmin(chatAdminDto));
  }

}
