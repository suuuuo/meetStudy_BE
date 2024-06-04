package com.elice.meetstudy.domain.scrap.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ScrapInsertDTO {

  @NotNull
  private Long userId;
  private Long categoryId;
  private Long postId;


  @Builder
  public ScrapInsertDTO(Long userId, Long categoryId, Long postId) {
    this.userId = userId;
    this.categoryId = categoryId;
    this.postId = postId;
  }

}
