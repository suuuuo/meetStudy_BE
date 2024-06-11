package com.elice.meetstudy.domain.chatroom.pagable;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class CustomPageRequest extends PageRequest implements CustomPageable {
  private Long cursor;

  public CustomPageRequest(int page, int size, Sort sort, Long cursor) {
    super(page, size, sort);
    this.cursor= cursor;
  }

  public CustomPageRequest(int page, int size, Long cursor) {
    this(page, size, Sort.unsorted(), cursor);
  }

  public void setCursor(Long cursor) {
    this.cursor = cursor;
  }

  @Override
  public Long getCursor() {
    return cursor;
  }
}
