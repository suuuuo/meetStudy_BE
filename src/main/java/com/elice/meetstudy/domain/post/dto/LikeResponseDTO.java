package com.elice.meetstudy.domain.post.dto;

import com.elice.meetstudy.domain.post.domain.PostLike;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LikeResponseDTO {

  // 상수로 설정. read only
  private final Long postId;
  private final String category;
  private final String nickname;
  private final String title;
  private final String content;
  private final Long hit;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy.MM.dd HH:mm")
  private final LocalDateTime createdAt;

  public LikeResponseDTO(PostLike like) {
    this.postId = like.getPost().getId();
    this.category = like.getPost().getCategory().getName();
    this.nickname = like.getPost().getUser().getNickname();
    this.title =
        like.getPost().getTitle().length() > 10
            ? like.getPost().getTitle().substring(0, 10)
            : like.getPost().getTitle();
    this.content = like.getPost().getContent();
    this.hit = like.getPost().getHit();
    this.createdAt = like.getCreatedAt();
  }
}
