package com.elice.meetstudy.domain.scrap.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ScrapInsertDTO {

  private Long categoryId;
  private Long postId;


  @Builder
  public ScrapInsertDTO(Long categoryId, Long postId) {
    this.categoryId = categoryId;
    this.postId = postId;
  }

}
