package com.elice.meetstudy.domain.post.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostEditDTO {

  private final String title;
  private final String content;

  public PostEditDTO(String title, String content) {
    this.title = title;
    this.content = content;
  }
}
