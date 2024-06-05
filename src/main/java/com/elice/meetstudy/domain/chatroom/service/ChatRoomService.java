package com.elice.meetstudy.domain.chatroom.service;

import com.elice.meetstudy.domain.chatroom.domain.ChatRoom;
import com.elice.meetstudy.domain.chatroom.dto.ChatRoomDto;
import com.elice.meetstudy.domain.chatroom.dto.CreateChatRoomDto;
import com.elice.meetstudy.domain.chatroom.repository.ChatRoomRepository;
import com.elice.meetstudy.domain.studyroom.exception.EntityNotFoundException;
import com.elice.meetstudy.domain.studyroom.repository.StudyRoomRepository;
import com.elice.meetstudy.domain.user.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatRoomService {


  @Autowired
  private final ChatRoomRepository chatRoomRepository;

//  @Autowired
//  private final MessageRepository messageRepository;

  @Autowired
  private final StudyRoomRepository studyRoomRepository;

  @Autowired
  private final UserRepository userRepository;

  //채팅방 생성하기
  public CreateChatRoomDto createchatRoom(CreateChatRoomDto chatRoomDto){
    ChatRoom createdChatRoom = ChatRoom.builder()
        .title(chatRoomDto.getTitle())
        .studyRoom(studyRoomRepository.findById(chatRoomDto.getStudyRoomId())
            .orElseThrow(()-> new EntityNotFoundException("해당 id의 스터디 룸을 찾을 수 없습니다.")))
        .notice(chatRoomDto.getNotice())
        .build();
    chatRoomRepository.save(createdChatRoom);
    return new CreateChatRoomDto(createdChatRoom);
  }
  //채팅방 삭제하기
  public void deleteChatRoom(Long chatRoomId){
        chatRoomRepository.deleteById(chatRoomId);
  }

  //채팅방 아이디로 찾기
  public ChatRoomDto findByChatRoomId(Long chatRoomId) {
    return new ChatRoomDto (chatRoomRepository.findById(chatRoomId)
        .orElseThrow(()-> new EntityNotFoundException("해당 채팅방이 존재하지 않습니다.")));
  }
  //채팅방 리스트 조회
  public List<ChatRoomDto> chatRoomList(Long studyRoomId){
    return chatRoomRepository.findChatRoomsByStudyRoomId(studyRoomId)
        .stream().map(chatRoom -> new ChatRoomDto(chatRoom)).toList();
  }

  //공지사항 등록
  public ChatRoomDto createNotice(ChatRoomDto chatRoomDto){
    ChatRoom chatRoom = chatRoomRepository.findById(chatRoomDto.getId()).orElseThrow(()->new EntityNotFoundException("해당 id의 채팅룸을 찾을 수 없습니다."));
    chatRoom.updateNotice(chatRoomDto.getNotice());
    return new ChatRoomDto(chatRoom);
  }


}
