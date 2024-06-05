package com.elice.meetstudy.domain.chatroom.repository;

import com.elice.meetstudy.domain.chatroom.domain.ChatRoom;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {

  @Query("select c from ChatRoom c join fetch c.studyRoom s where s.id = :studyRoomId")
  List<ChatRoom> findChatRoomsByStudyRoomId(Long studyRoomId);
}
