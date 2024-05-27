package com.elice.meetstudy.domain.chatroom.service;

import com.elice.meetstudy.domain.chatroom.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

  @Autowired
  private final ChatRoomRepository chatRoomRepository;


}
