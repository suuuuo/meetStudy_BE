package com.elice.meetstudy.domain.chatroom.service;

import com.elice.meetstudy.domain.chatroom.domain.Message;
import com.elice.meetstudy.domain.chatroom.dto.MessageModel;
import com.elice.meetstudy.domain.chatroom.dto.OutputMessageModel;
import com.elice.meetstudy.domain.chatroom.repository.ChatRoomRepository;
import com.elice.meetstudy.domain.chatroom.repository.MessageRepository;
import com.elice.meetstudy.domain.studyroom.exception.EntityNotFoundException;
import com.elice.meetstudy.domain.user.domain.User;
import com.elice.meetstudy.domain.user.domain.UserPrinciple;
import com.elice.meetstudy.domain.user.repository.UserRepository;
import com.elice.meetstudy.util.EntityFinder;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MessageService {

  @Autowired
  private final ChatRoomRepository chatRoomRepository;

  @Autowired
  private final MessageRepository messageRepository;

  @Autowired
  private final UserRepository userRepository;

  @Autowired
  EntityFinder entityFinder;

  //메세지를 데이터베이스에 저장 후 보내기
  public OutputMessageModel sendMessage(MessageModel messageModel, Long chatRoomId){

    User user = userRepository.findUserByUserId(messageModel.getUserId());

    Message message = Message.builder()
        .chatRoom(chatRoomRepository.findChatRoomByIdAndUserId(chatRoomId, messageModel.getUserId())
            .orElseThrow(()->new EntityNotFoundException("채팅방에 참여할 수 없습니다.")))
        .createAt(LocalDateTime.now())
        .sender(user)
        .content(messageModel.getContent())
          .build();
      messageRepository.save(message);

      return new OutputMessageModel(message);
}

  //채팅방의 메세지 조회
  public Page<OutputMessageModel> messages (Long chatRoomId) {
    Pageable pageable = PageRequest.of(0,50, Sort.by("createAt").descending());

    Page<Message> messages = messageRepository.findMessagesWithChatRoomAndUsers(
        chatRoomId, pageable);
    return messages.map(message -> new OutputMessageModel(message));
  }

  public OutputMessageModel enterMessage(Long chatRoomId){

//    User user = entityFinder.getUser();
    User user = userRepository.findUserByUserId(1L);

    Message message = Message.builder()
        .chatRoom(chatRoomRepository.findChatRoomByIdAndUserId(chatRoomId, user.getId())
            .orElseThrow(()->new EntityNotFoundException("채팅방에 참여할 수 없습니다.")))
        .createAt(LocalDateTime.now())
        .sender(user)
        .content(user.getNickname()+"님이 입장하셨습니다.")
        .build();
    messageRepository.save(message);

    return new OutputMessageModel(message);

  }

  public OutputMessageModel exitMessage(Long chatRoomId){
    User user = entityFinder.getUser();

    Message message = Message.builder()
        .chatRoom(chatRoomRepository.findChatRoomByIdAndUserId(chatRoomId, user.getId())
            .orElseThrow(()->new EntityNotFoundException("채팅방에 참여할 수 없습니다.")))
        .createAt(LocalDateTime.now())
        .sender(user)
        .content(user.getNickname()+"님이 퇴장하셨습니다.")
        .build();
    messageRepository.save(message);

    return new OutputMessageModel(message);

  }
}