package com.elice.meetstudy.domain.chatroom.service;

import com.elice.meetstudy.domain.chatroom.domain.Message;
import com.elice.meetstudy.domain.chatroom.dto.MessageModel;
import com.elice.meetstudy.domain.chatroom.dto.OutputMessageModel;
import com.elice.meetstudy.domain.chatroom.repository.ChatRoomRepository;
import com.elice.meetstudy.domain.chatroom.repository.MessageRepository;
import com.elice.meetstudy.domain.user.domain.User;
import com.elice.meetstudy.domain.user.repository.UserRepository;
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

@Service
@RequiredArgsConstructor
public class MessageService {

  @Autowired
  private final ChatRoomRepository chatRoomRepository;

  @Autowired
  private final MessageRepository messageRepository;

  @Autowired
  private final UserRepository userRepository;


  //메세지를 데이터베이스에 저장 후 보내기
  public OutputMessageModel sendMessage(MessageModel messageModel, Long chatRoomId){

//    User byNickname = userRepository.findUserByNickname(messageModel.getNickName());
    Message message = Message.builder()
          .chatRoom(chatRoomRepository.findById(chatRoomId).get())
          .createAt(LocalDateTime.now())
//          .sender(byNickname)
          .content(messageModel.getContent())
          .build();
      messageRepository.save(message);
      return new OutputMessageModel(chatRoomId,
          messageModel.getNickName(), messageModel.getContent(),
          message.getCreateAt().toString());
}
    //메세지 저장
    //메세지 전송

//  //채팅방의 메세지 조회
//  public Page<MessageModel> messages (Long chatRoomId) {
//    Pageable pageable = PageRequest.of(0,50, Sort.by("createdAt").descending());
//
//    Page<Message> messages = messageRepository.findChatRoomWithMessagesAndUsers(
//        chatRoomId, pageable);
//    return messages.map(message -> new MessageModel(message));
//  }

}