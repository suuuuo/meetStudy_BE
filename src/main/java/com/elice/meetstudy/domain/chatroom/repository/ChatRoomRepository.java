package com.elice.meetstudy.domain.chatroom.repository;

import com.elice.meetstudy.domain.chatroom.domain.ChatRoom;
import com.elice.meetstudy.domain.user.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

  @Query("select c from ChatRoom c "
      + "join fetch c.studyRoom s "
      + "where s.id = :studyRoomId")
  List<ChatRoom> findChatRoomsByStudyRoomId(Long studyRoomId);

  @Query("select c from ChatRoom c "
      + "join fetch c.studyRoom s "
      + "join fetch UserStudyRoom us "
      + "on us.studyRoom = s "
      + "where c.id = :id and us.user.id = :userId")
  Optional<ChatRoom> findChatRoomByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);

  @Query("select c from ChatRoom c "
      + "join fetch c.studyRoom s "
      + "join fetch UserStudyRoom us "
      + "on us.studyRoom = s "
      + "where us.user.id = :userId")
  List<ChatRoom> findChatRoomsByUserId(@Param("userId") Long userId);

  @Query("select c from ChatRoom "
      + "c where c.id = :chatRoomId")
  Optional<ChatRoom> findChatRoomById(Long chatRoomId);
}
