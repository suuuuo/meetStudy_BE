package com.elice.meetstudy.domain.chatroom.repository;

import com.elice.meetstudy.domain.chatroom.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {

}
