package com.elice.meetstudy.domain.chatroom.service;

import com.elice.meetstudy.domain.chatroom.domain.Message;
import com.elice.meetstudy.domain.chatroom.dto.MessageDto;
import com.elice.meetstudy.domain.chatroom.repository.ChatRoomRepository;
import com.elice.meetstudy.domain.chatroom.repository.MessageRepository;
import com.elice.meetstudy.domain.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Service
@RequiredArgsConstructor
public class MessageService {

  private final ObjectMapper mapper;

  @Autowired
  private final ChatRoomRepository chatRoomRepository;

  @Autowired
  private final MessageRepository messageRepository;

  @Autowired
  private final UserRepository userRepository;


  //메세지를 데이터베이스에 저장 후 보내기
  public void sendMessageToAllSession(Long id, MessageDto messageDto) {

    //chatroom 각각의 세션
//    Set<WebSocketSession> sessions = chatRoomRepository.findById(id).get().getSessions();

    //메세지 저장
    messageRepository.save(
        Message.builder()
        .createAt(LocalDateTime.now())
        .content(messageDto.getContent())
        .chatRoom(chatRoomRepository.findById(messageDto.getChatRoomId()).get())
//        .user(userRepository.findById(messageDto.getUserId()).get())
        .build()
        );

    //메세지 전송
//    for (WebSocketSession session : sessions) {
//      try {
//        session.sendMessage(new TextMessage(mapper.writeValueAsString(messageDto)));
//      } catch (IOException e) {
//        throw new RuntimeException(e);
//      }
//    }
  }


}