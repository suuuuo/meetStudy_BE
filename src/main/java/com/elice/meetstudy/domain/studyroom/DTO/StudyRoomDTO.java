package com.elice.meetstudy.domain.studyroom.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyRoomDTO {
  private Long id;
  private String title;
  private String description;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy.MM.dd HH:mm")
  private Date createdDate;

  private Long maxCapacity;
  // private Long categoryId;
  @Builder.Default private List<UserStudyRoomDTO> userStudyRooms = new ArrayList<>();
}
