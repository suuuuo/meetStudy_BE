package com.elice.meetstudy.domain.chatroom.service;

import com.elice.meetstudy.domain.chatroom.domain.ChatRoom;
import com.elice.meetstudy.domain.chatroom.dto.ChatRoomDto;
import com.elice.meetstudy.domain.chatroom.dto.CreateChatRoomDto;
import com.elice.meetstudy.domain.chatroom.repository.ChatRoomRepository;
import com.elice.meetstudy.domain.studyroom.entity.StudyRoom;
import com.elice.meetstudy.domain.studyroom.exception.EntityNotFoundException;
import com.elice.meetstudy.domain.studyroom.repository.StudyRoomRepository;
import com.elice.meetstudy.util.EntityFinder;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatRoomService {

  @Autowired private final ChatRoomRepository chatRoomRepository;

  @Autowired private final EntityFinder entityFinder;

  @Autowired private final StudyRoomRepository studyRoomRepository;

  // 채팅방 생성하기
  public CreateChatRoomDto createchatRoom(CreateChatRoomDto createChatRoomDto) {

    StudyRoom studyRoom =
        studyRoomRepository
            .findStudyRoomByIdAndUserId(
                createChatRoomDto.getStudyRoomId(), entityFinder.getUser().getId())
            .orElseThrow(() -> new EntityNotFoundException("해당 스터디룸에 접근할 수 없습니다."));

    ChatRoom createdChatRoom =
        ChatRoom.builder()
            .title(createChatRoomDto.getTitle())
            .studyRoom(studyRoom)
            .notice(createChatRoomDto.getNotice())
            .build();
    chatRoomRepository.save(createdChatRoom);
    return new CreateChatRoomDto(createdChatRoom);
  }

  // 채팅방 삭제하기
  public void deleteChatRoom(Long chatRoomId) {

    chatRoomRepository
        .findChatRoomByIdAndUserId(chatRoomId, entityFinder.getUser().getId())
        .orElseThrow(() -> new EntityNotFoundException("접근할 수 없습니다."));

    chatRoomRepository.deleteById(chatRoomId);
  }

  // 채팅방 아이디로 찾기
  public ChatRoomDto findByChatRoomId(Long chatRoomId) {

    return new ChatRoomDto(
        chatRoomRepository
            .findChatRoomByIdAndUserId(chatRoomId, entityFinder.getUser().getId())
            .orElseThrow(() -> new EntityNotFoundException("접근할 수 없습니다.")));
  }

  // 채팅방 리스트 조회
  public List<ChatRoomDto> chatRoomList(Long studyRoomId) {

    return studyRoomRepository
        .findStudyRoomByIdAndUserId(studyRoomId, entityFinder.getUser().getId())
        .orElseThrow(() -> new EntityNotFoundException("해당 스터디룸에 접근할 수 없습니다."))
        .getChatRooms()
        .stream()
        .map(chatRoom -> new ChatRoomDto(chatRoom))
        .toList();
  }

  // 공지사항 수정
  public ChatRoomDto createNotice(ChatRoomDto chatRoomDto) {
    ChatRoom chatRoom =
        chatRoomRepository
            .findChatRoomByIdAndUserId(chatRoomDto.getId(), entityFinder.getUser().getId())
            .orElseThrow(() -> new EntityNotFoundException("접근할 수 없습니다."));
    chatRoom.updateNotice(chatRoomDto.getNotice());
    return new ChatRoomDto(chatRoom);
  }
}
