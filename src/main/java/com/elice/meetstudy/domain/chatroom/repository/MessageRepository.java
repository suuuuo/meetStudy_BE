package com.elice.meetstudy.domain.chatroom.repository;

import com.elice.meetstudy.domain.chatroom.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message,Long> {

}
