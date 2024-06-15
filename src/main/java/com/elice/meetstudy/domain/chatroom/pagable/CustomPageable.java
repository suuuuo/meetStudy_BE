package com.elice.meetstudy.domain.chatroom.pagable;


import org.springframework.data.domain.Pageable;

public interface CustomPageable extends Pageable {

  Long getCursor();
}
