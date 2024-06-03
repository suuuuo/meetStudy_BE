package com.elice.meetstudy.domain.chatroom.repository;

import com.elice.meetstudy.domain.chatroom.domain.ChatRoom;
import com.elice.meetstudy.domain.chatroom.domain.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {

  @Query("select c from Chatroom c "
      + "left join fetch c.message m "
      + "left join fetch m.user u "
      + "where c.id = :id")
  Page<Message> findChatMessageByChatRoomId(Long id,Pageable pageable);
}
