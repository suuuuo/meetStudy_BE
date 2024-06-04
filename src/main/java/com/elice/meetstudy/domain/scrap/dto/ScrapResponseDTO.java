package com.elice.meetstudy.domain.scrap.dto;

import com.elice.meetstudy.domain.scrap.domain.Scrap;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ScrapResponseDTO {

  private String category; // 게시판
  private String title; // 게시글 제목
  private String content; // 게시글 내용

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy.MM.dd HH:mm")
  private LocalDateTime createdAt;

  public ScrapResponseDTO(Scrap scrap) {
    this.category = scrap.getCategory().getName();
    this.title = scrap.getPost().getTitle();
    this.content = scrap.getPost().getContent();
    this.createdAt = scrap.getCreatedAt();
  }
}
