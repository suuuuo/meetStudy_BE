package com.elice.meetstudy.domain.chatroom.service;

import com.elice.meetstudy.domain.chatroom.domain.ChatRoom;
import com.elice.meetstudy.domain.chatroom.domain.Message;
import com.elice.meetstudy.domain.chatroom.dto.ChatRoomDto;
import com.elice.meetstudy.domain.chatroom.dto.MessageDto;
import com.elice.meetstudy.domain.chatroom.repository.ChatRoomRepository;
import com.elice.meetstudy.domain.chatroom.repository.MessageRepository;
import com.elice.meetstudy.domain.studyroom.service.StudyRoomService;
import com.elice.meetstudy.domain.user.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

@Service
@RequiredArgsConstructor
public class ChatRoomService {


  @Autowired
  private final ChatRoomRepository chatRoomRepository;

  @Autowired
  private final MessageRepository messageRepository;

  @Autowired
  private final StudyRoomService studyRoomService;

  @Autowired
  private final UserRepository userRepository;

  //채팅방 생성하기
//  public ChatRoom chatRoom(Long id){
//    chatRoomRepository.save(ChatRoom.builder()
//        .studyRoom(studyRoomService.getStudyRoomById(id).get()));
//  }

  //채팅방 아이디로 찾기
  public Optional<ChatRoom> findByChatRoomId(Long id) {
    return chatRoomRepository.findById(id);
  }
  //채팅방 삭제

  //공지사항 등록
  public Optional<ChatRoom> create(Long chatRoomId, String notice){
    ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).get();
    chatRoom.updateNotice(notice);
    return Optional.of(chatRoom);
  }

  //채팅방 세션 저장
  public void sessionSave(Long chatRoomId, WebSocketSession session) {
    findByChatRoomId(chatRoomId).get().getSessions().add(session);
  }

  //채팅방의 메세지 조회
  public Page<MessageDto> messages (Long chatRoomId) {
    Pageable pageable = PageRequest.of(0,50, Sort.by("createdAt").descending());

    Page<Message> messages = chatRoomRepository.findChatMessageByChatRoomId(
        chatRoomId, pageable);
    return messages.map(message -> new MessageDto(message));
  }
}
