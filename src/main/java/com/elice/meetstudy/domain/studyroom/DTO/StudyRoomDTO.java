package com.elice.meetstudy.domain.studyroom.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder(builderClassName = "StudyRoomDTOBuilder", toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class StudyRoomDTO {
    private Long id;
    private String title;
    private String description;
    private Date createdDate;
    private Long maxCapacity;
    //private Long categoryId;
    private List<UserStudyRoomDTO> userStudyRooms = new ArrayList<>();

    public static class StudyRoomDTOBuilder {
        public StudyRoomDTOBuilder() {
            this.userStudyRooms = new ArrayList<>();
        }
    }

    public void addUserStudyRoom(UserStudyRoomDTO userStudyRoomDTO) {
        this.userStudyRooms.add(userStudyRoomDTO);
    }
}
