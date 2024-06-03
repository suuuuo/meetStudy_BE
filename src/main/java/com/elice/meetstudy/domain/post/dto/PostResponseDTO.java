package com.elice.meetstudy.domain.post.dto;

import com.elice.meetstudy.domain.post.domain.Post;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostResponseDTO {

  // 상수로 설정. read only
  private final Long id;
  // private final Long categoryId;
  private final String category;
  //  private final Long userId;
  private final String nickname;
  private final String title;
  private final String content;
  private final Long hit;
  private final LocalDateTime createdAt;

  public PostResponseDTO(Post post) {
    this.id = post.getId();
    //    this.categoryId = post.getCategory().getId();
    //    this.userId = post.getUser().getId();
    this.category = post.getCategory().getName();
    this.nickname = post.getUser().getNickname();
    this.title = post.getTitle().length() > 10 ? post.getTitle().substring(0, 10) : post.getTitle();
    this.content = post.getContent();
    this.hit = post.getHit();
    this.createdAt = post.getCreatedAt();
  }
}
