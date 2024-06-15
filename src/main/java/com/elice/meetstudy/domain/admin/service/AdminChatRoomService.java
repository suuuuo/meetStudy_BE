package com.elice.meetstudy.domain.admin.service;

import com.elice.meetstudy.domain.chatroom.domain.ChatRoom;
import com.elice.meetstudy.domain.chatroom.dto.ChatRoomDto;
import com.elice.meetstudy.domain.chatroom.repository.ChatRoomRepository;
import com.elice.meetstudy.domain.studyroom.exception.EntityNotFoundException;
import com.elice.meetstudy.util.EntityFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    @Autowired
    public AdminChatRoomService(ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    // 모든 채팅방 조회
    public List<ChatRoomDto> getAllChatRooms() {
        return chatRoomRepository.findAll()
                .stream()
                .map(ChatRoomDto::new)
                .collect(Collectors.toList());
    }

    // id에 해당하는 채팅방 조회
    public ChatRoomDto getChatRoomById(Long chatRoomId) {
        return new ChatRoomDto(
                chatRoomRepository.findById(chatRoomId)
                        .orElseThrow(() -> new EntityNotFoundException("해당 id의 채팅방을 찾을 수 없습니다. [ID: " + chatRoomId + "]"))
        );
    }

    // 특정 회원이 속한 채팅방 조회
    public List<ChatRoomDto> getChatRoomsByUserId(Long userId) {
        return chatRoomRepository.findChatRoomsByUserId(userId)
                .stream()
                .map(ChatRoomDto::new)
                .collect(Collectors.toList());
    }

    // 채팅방 삭제
    public void deleteChatRoom(Long chatRoomId) {
        chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id의 채팅방을 찾을 수 없습니다. [ID: " + chatRoomId + "]"));
        chatRoomRepository.deleteById(chatRoomId);
    }
}
