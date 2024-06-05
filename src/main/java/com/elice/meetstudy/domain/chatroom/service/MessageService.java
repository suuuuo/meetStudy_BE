package com.elice.meetstudy.domain.chatroom.service;

import com.elice.meetstudy.domain.chatroom.domain.Message;
import com.elice.meetstudy.domain.chatroom.dto.MessageModel;
import com.elice.meetstudy.domain.chatroom.dto.OutputMessageModel;
import com.elice.meetstudy.domain.chatroom.repository.ChatRoomRepository;
import com.elice.meetstudy.domain.chatroom.repository.MessageRepository;
import com.elice.meetstudy.domain.studyroom.exception.EntityNotFoundException;
import com.elice.meetstudy.domain.user.domain.User;
import com.elice.meetstudy.domain.user.repository.UserRepository;
import com.elice.meetstudy.util.EntityFinder;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageService {

  @Autowired
  private final ChatRoomRepository chatRoomRepository;

  @Autowired
  private final MessageRepository messageRepository;

  @Autowired
  private final EntityFinder entityFinder;


  //메세지를 데이터베이스에 저장 후 보내기
  public OutputMessageModel sendMessage(MessageModel messageModel, Long chatRoomId){

    User user = entityFinder.getUser();

    Message message = Message.builder()
          .chatRoom(chatRoomRepository.findById(chatRoomId).orElseThrow(()->new EntityNotFoundException("채팅방이 존재하지 않습니다.")))
          .createAt(LocalDateTime.now())
          .sender(user)
          .content(messageModel.getContent())
          .build();
      messageRepository.save(message);

      return OutputMessageModel.builder()
          .chatRoomId(chatRoomId)
          .createdAt(message.getCreateAt().toString())
          .nickName(user.getNickname())
          .content(message.getContent())
          .build();
}

  //채팅방의 메세지 조회
  public Page<MessageModel> messages (Long chatRoomId) {
    Pageable pageable = PageRequest.of(0,50, Sort.by("createdAt").descending());

    Page<Message> messages = messageRepository.findMessagesWithChatRoomAndUsers(
        chatRoomId, pageable);
    return messages.map(message -> new MessageModel(message));
  }

}