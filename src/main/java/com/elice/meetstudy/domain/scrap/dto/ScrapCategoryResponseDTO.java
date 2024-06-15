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
    if (scrap.getCategory() != null) {
      this.category = scrap.getCategory().getName();
    } else {
      this.category = null; // 카테고리가 null인 경우에는 null을 반환
    }
    this.createdAt = scrap.getCreatedAt();
  }
}
