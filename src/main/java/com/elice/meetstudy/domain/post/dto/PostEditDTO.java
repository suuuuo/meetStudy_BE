package com.elice.meetstudy.domain.post.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostEditDTO {

  private final Long categoryId;
  private final String title;
  private final String content;

  @Builder
  public PostEditDTO(Long categoryId, String title, String content) {
    this.categoryId = categoryId;
    this.title = title;
    this.content = content;
  }
}
