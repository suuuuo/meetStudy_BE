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

  @Query("SELECT m FROM Message m "
      + "JOIN FETCH m.sender s "
      + "JOIN FETCH m.chatRoom c "
      + "WHERE c.id = :chatRoomId "
      + "and m.id < :cursor "
      + "and m.id >= (select MIN(m2.id) from Message m2 "
      + "where m2.sender.id= :senderId "
      + "And m2.chatRoom.id =:chatRoomId) "
      + "order by m.id DESC")
  Page<Message> findMessagesWithChatRoomAndUsers(@Param("chatRoomId") Long chatRoomId, @Param("senderId") Long senderId, @Param("cursor") Long cursor,Pageable pageable);

  @Query("SELECT m FROM Message m "
      + "JOIN FETCH m.sender s "
      + "JOIN FETCH m.chatRoom c "
      + "WHERE c.id = :chatRoomId "
      + "and m.id >= (select MIN(m2.id) from Message m2 "
      + "where m2.sender.id= :senderId "
      + "And m2.chatRoom.id =:chatRoomId) "
      + "order by m.id DESC")
  Page<Message> findLatestMessagesWithChatRoomAndUsers(@Param("chatRoomId") Long chatRoomId, @Param("senderId") Long senderId,Pageable pageable);



}
