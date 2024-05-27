package com.elice.meetstudy.domain.chatroom.handler;

import com.elice.meetstudy.domain.chatroom.dto.MessageDto;
import com.elice.meetstudy.domain.chatroom.service.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@RequiredArgsConstructor
public class WebSocketChatHandler extends TextWebSocketHandler {

  private final ObjectMapper mapper;
  @Autowired
  private final ChatService chatService;

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    String payload = message.getPayload();
    MessageDto messageDto = mapper.readValue(payload,MessageDto.class);
    chatService.sessionSave(messageDto.getChatRoomId(), session);
    chatService.sendMessageToAllSession(messageDto.getChatRoomId(),messageDto);
  }

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {

  }
}
