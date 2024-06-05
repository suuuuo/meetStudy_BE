package com.elice.meetstudy.domain.chatroom.service;

import com.elice.meetstudy.domain.chatroom.domain.ChatRoom;
import com.elice.meetstudy.domain.chatroom.repository.ChatRoomRepository;
import com.elice.meetstudy.domain.chatroom.repository.MessageRepository;
import com.elice.meetstudy.domain.studyroom.mapper.StudyRoomMapper;
import com.elice.meetstudy.domain.studyroom.service.StudyRoomService;
import com.elice.meetstudy.domain.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class ChatRoomService {


  @Autowired
  private final ChatRoomRepository chatRoomRepository;

//  @Autowired
//  private final MessageRepository messageRepository;

  @Autowired
  private final StudyRoomMapper studyRoomMapper;

  @Autowired
  private final StudyRoomService studyRoomService;

  @Autowired
  private final UserRepository userRepository;

  //채팅방 생성하기
  public ChatRoom chatRoom(Long id){
    ChatRoom createdChatRoom = ChatRoom.builder()
        .studyRoom(studyRoomMapper.toStudyRoom(studyRoomService.getStudyRoomById(id)))
        .build();
    chatRoomRepository.save(createdChatRoom);
        return createdChatRoom;
  }

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

}
