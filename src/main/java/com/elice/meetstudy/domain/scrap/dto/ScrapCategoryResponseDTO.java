package com.elice.meetstudy.domain.scrap.dto;

import com.elice.meetstudy.domain.scrap.domain.Scrap;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScrapCategoryResponseDTO {

  private String category; // 게시판 이름

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy.MM.dd HH:mm")
  private LocalDateTime createdAt;

  public ScrapCategoryResponseDTO(Scrap scrap) {
    this.category = scrap.getCategory().getName();
    this.createdAt = scrap.getCreatedAt();
  }
}
