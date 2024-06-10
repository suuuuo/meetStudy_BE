package com.elice.meetstudy.domain.admin.controller;

import com.elice.meetstudy.domain.admin.service.AdminChatRoomService;
import com.elice.meetstudy.domain.chatroom.dto.ChatRoomDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/chatrooms")
@Tag(name = "C. 관리자")
public class AdminChatRoomController {

    private final AdminChatRoomService adminChatRoomService;

    @Autowired
    public AdminChatRoomController(AdminChatRoomService adminChatRoomService) {
        this.adminChatRoomService = adminChatRoomService;
    }

    @Operation(summary = "모든 채팅방 조회")
    @GetMapping
    public ResponseEntity<List<ChatRoomDto>> getAllChatRooms() {
        return ResponseEntity.ok(adminChatRoomService.getAllChatRooms());
    }

    @Operation(summary = "id에 해당하는 채팅방 조회")
    @GetMapping("/{chatRoomId}")
    public ResponseEntity<ChatRoomDto> chatRoomDto(@PathVariable Long chatRoomId) {
        return ResponseEntity.ok(adminChatRoomService.getChatRoomById(chatRoomId));
    }

    @Operation(summary = "특정 회원이 속한 채팅방 조회")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ChatRoomDto>> getChatRoomsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(adminChatRoomService.getChatRoomsByUserId(userId));
    }

    @Operation(summary = "채팅방 삭제")
    @DeleteMapping("/{chatRoomId}")
    public ResponseEntity<Void> deleteChatRoom(@PathVariable Long chatRoomId) {
        adminChatRoomService.deleteChatRoom(chatRoomId);
        return ResponseEntity.noContent().build();
    }
}
