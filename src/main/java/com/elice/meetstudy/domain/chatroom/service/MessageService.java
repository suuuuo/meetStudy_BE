package com.elice.meetstudy.domain.chatroom.service;

import com.elice.meetstudy.domain.chatroom.domain.Message;
import com.elice.meetstudy.domain.chatroom.dto.MessageModel;
import com.elice.meetstudy.domain.chatroom.dto.OutputMessageModel;
import com.elice.meetstudy.domain.chatroom.pagable.CustomPageRequest;
import com.elice.meetstudy.domain.chatroom.repository.ChatRoomRepository;
import com.elice.meetstudy.domain.chatroom.repository.MessageRepository;
import com.elice.meetstudy.domain.studyroom.exception.EntityNotFoundException;
import com.elice.meetstudy.domain.user.domain.User;
import com.elice.meetstudy.util.EntityFinder;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
  EntityFinder entityFinder;

  //메세지를 데이터베이스에 저장 후 보내기
  public OutputMessageModel sendMessage(MessageModel messageModel, Long chatRoomId, User user) {

    Message message = Message.builder()
        .chatRoom(chatRoomRepository.findChatRoomById(chatRoomId)
            .orElseThrow(() -> new EntityNotFoundException("채팅방에 참여할 수 없습니다.")))
        .createAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
        .sender(user)
        .content(messageModel.getContent())
        .build();
    messageRepository.save(message);

    return new OutputMessageModel(message);
  }

  //채팅방의 메세지 조회
  public Page<OutputMessageModel> messages(Long chatRoomId, Long cursor) {

    CustomPageRequest pageable = new CustomPageRequest(0, 20, cursor);

    if (cursor == -1) {
      Page<Message> messages = messageRepository.findLatestMessagesWithChatRoomAndUsers(
          chatRoomId, entityFinder.getUser().getId(), pageable);
      changeCursor(messages, pageable);
      return messages.map(message -> new OutputMessageModel(message));
    } else {

      Page<Message> messages = messageRepository.findMessagesWithChatRoomAndUsers(
          chatRoomId, entityFinder.getUser().getId(), cursor, pageable);
      changeCursor(messages, pageable);
      return messages.map(message -> new OutputMessageModel(message));
    }
  }

  private static void changeCursor(Page<Message> messages, CustomPageRequest pageable) {
    if (messages.get().toList().isEmpty()) {
      pageable.setCursor(0L);
    } else {
      pageable.setCursor(messages.get().toList().get(messages.get().toList().size() - 1).getId());
    }
  }

  //입장메세지
  public OutputMessageModel enterMessage(Long chatRoomId, User user) {

    Message message = Message.builder()
        .chatRoom(chatRoomRepository.findChatRoomByIdAndUserId(chatRoomId, user.getId())
            .orElseThrow(() -> new EntityNotFoundException("채팅방에 참여할 수 없습니다.")))
        .createAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
        .sender(user)
        .content(user.getNickname() + "님이 입장하셨습니다.")
        .build();
    messageRepository.save(message);

    return new OutputMessageModel(message);

  }

  //퇴장메세지
  public OutputMessageModel exitMessage(Long chatRoomId, User user) {

    Message message = Message.builder()
        .chatRoom(chatRoomRepository.findChatRoomById(chatRoomId)
            .orElseThrow(() -> new EntityNotFoundException("채팅방에 참여할 수 없습니다.")))
        .createAt(LocalDateTime.now())
        .sender(user)
        .content(user.getNickname() + "님이 퇴장하셨습니다.")
        .build();
    messageRepository.save(message);

    return new OutputMessageModel(message);

  }
}