package com.elice.meetstudy.domain.studyroom.controller;

import com.elice.meetstudy.domain.studyroom.DTO.EmailBodyDTO;
import com.elice.meetstudy.domain.studyroom.DTO.StudyRoomDTO;
import com.elice.meetstudy.domain.studyroom.DTO.UserStudyRoomDTO;
import com.elice.meetstudy.domain.studyroom.service.StudyRoomService;
import com.elice.meetstudy.domain.studyroom.service.UserStudyRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/userstudyrooms")
@Tag(name = "유저스터디룸", description = "스터디룸 권한에 관한 유저 API 입니다.")
public class UserStudyRoomController {
    @Autowired
    private UserStudyRoomService userStudyRoomService;

    @Operation(summary = "모든 유저의 스터디룸에 대한 권한 조회", description = "모든 유저들이 각각 참여하고 있는 스터디룸들에서의 권한을 모두 조회합니다.")
    @GetMapping
    public List<UserStudyRoomDTO> getAllUserStudyRooms() {
        return userStudyRoomService.getAllUserStudyRooms();
    }

    @PostMapping("/add/{id}")
    @Operation(summary = "스터디룸 참가", description = "유저의 이메일을 사용하여, 해당 유저를 스터디룸에 참가시킵니다.")
    @UserStudyRoomAnnotation.Success(description = "성공적으로 참가함")
    @StudyRoomAnnotation.Failure
    public ResponseEntity<UserStudyRoomDTO> joinStudyRoom(
            @Parameter(description = "참가할 스터디룸의 ID", required = true)
            @PathVariable Long id,
            @Parameter(description = "참가할 유저의 EMAIL", required = true)
            @RequestBody EmailBodyDTO emailBodyDTO) {
        UserStudyRoomDTO createdStudyRoom = userStudyRoomService.joinStudyRoom(id, emailBodyDTO.getEmail());
        return ResponseEntity.ok(createdStudyRoom);
    }
}
