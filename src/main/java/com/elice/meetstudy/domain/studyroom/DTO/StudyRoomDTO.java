package com.elice.meetstudy.domain.studyroom.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyRoomDTO {
    private Long id;
    private String title;
    private String description;
    private Date createdDate;
    private Long maxCapacity;
    //private Long categoryId;
    @Builder.Default
    private List<UserStudyRoomDTO> userStudyRooms = new ArrayList<>();
}
