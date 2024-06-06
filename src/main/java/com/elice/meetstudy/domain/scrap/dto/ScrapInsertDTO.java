package com.elice.meetstudy.domain.scrap.dto;

import jakarta.validation.constraints.Null;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ScrapInsertDTO {

  @Null private Long categoryId;
  @Null private Long postId;

  @Builder
  public ScrapInsertDTO(Long categoryId, Long postId) {
    this.categoryId = categoryId;
    this.postId = postId;
  }
}
