package com.elice.meetstudy.domain.studyroom.DTO;

import com.elice.meetstudy.domain.user.dto.UserLoginDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserStudyRoomDTO {
    private Long id;

    private Date joinDate;
    private String permission;

    private UserLoginDto user;
}
