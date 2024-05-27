package com.elice.meetstudy.domain.studyroom.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyRoomDTO {
    private Long id;
    private String title;
    private String description;
    private Date createdDate;
    private Long maxCapacity;
    //private Long categoryId;
}
