package com.elice.meetstudy.domain.post.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LikeInsertDTO {

  @NotNull private Long postId;

  @NotNull private Long userId;

  @Builder
  public LikeInsertDTO(Long postId, Long userId) {
    this.postId = postId;
    this.userId = userId;
  }
}
