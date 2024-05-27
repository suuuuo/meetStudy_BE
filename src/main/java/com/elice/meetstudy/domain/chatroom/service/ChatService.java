package com.elice.meetstudy.domain.chatroom.service;

import com.elice.meetstudy.domain.chatroom.domain.ChatRoom;
import com.elice.meetstudy.domain.chatroom.dto.MessageDto;
import com.elice.meetstudy.domain.chatroom.repository.ChatRoomRepository;
import com.elice.meetstudy.domain.chatroom.repository.MessageRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Service
@RequiredArgsConstructor
public class ChatService {

  private final ObjectMapper mapper;

  @Autowired
  private final ChatRoomRepository chatRoomRepository;

  @Autowired
  private final MessageRepository messageRepository;

  //채팅방 아이디로 찾기
  public Optional<ChatRoom> findByChatRoomId(Long id) {
    return chatRoomRepository.findById(id);
  }
  //채팅방 삭제

  //채팅방 세션 저장
  public void sessionSave(Long chatRoomId,WebSocketSession session) {
    findByChatRoomId(chatRoomId).get().getSessions().add(session);
  }

  //메세지를 데이터베이스에 저장 후 보내기
  public void sendMessageToAllSession(Long id, MessageDto messageDto) {

    Set<WebSocketSession> sessions = chatRoomRepository.findById(id).get().getSessions();

    for (WebSocketSession session : sessions) {
      try {
        session.sendMessage(new TextMessage(mapper.writeValueAsString(messageDto)));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
//      messageRepository(new Message());

    }
  }
}