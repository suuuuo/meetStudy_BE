package com.elice.meetstudy.domain.chatroom.repository;

import com.elice.meetstudy.domain.chatroom.domain.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {

//  @Query("SELECT m FROM Message m JOIN FETCH m.user u JOIN FETCH m.chatRoom c WHERE c.id = :chatRoomId")
//  Page<Message> findMessagesWithChatRoomAndUsers(@Param("chatRoomId") Long chatRoomId, Pageable pageable);
}
