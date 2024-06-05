package com.elice.meetstudy.domain.scrap.dto;

import com.elice.meetstudy.domain.scrap.domain.Scrap;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ScrapPostResponseDTO {

  private String title; // 게시글
  private String content;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy.MM.dd HH:mm")
  private LocalDateTime createdAt;

  public ScrapPostResponseDTO(Scrap scrap) {
    this.title = scrap.getPost().getTitle();
    this.content = scrap.getPost().getContent();
    this.createdAt = scrap.getCreatedAt();
  }
}
