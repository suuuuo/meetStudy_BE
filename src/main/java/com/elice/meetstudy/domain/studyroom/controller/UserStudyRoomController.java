package com.elice.meetstudy.domain.studyroom.controller;

import com.elice.meetstudy.domain.studyroom.DTO.UserStudyRoomDTO;
import com.elice.meetstudy.domain.studyroom.service.UserStudyRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/userstudyrooms")
@Tag(name = "J. 유저스터디룸", description = "스터디룸 권한에 관한 유저 API 입니다.")
public class UserStudyRoomController {
  @Autowired private UserStudyRoomService userStudyRoomService;

  @Operation(
      summary = "모든 유저의 스터디룸에 대한 권한 조회",
      description = "모든 유저들이 각각 참여하고 있는 스터디룸들에서의 권한을 모두 조회합니다.")
  @GetMapping
  public List<UserStudyRoomDTO> getAllUserStudyRooms() {
    return userStudyRoomService.getAllUserStudyRooms();
  }

  @Operation(
          summary = "현재 유저가 참가 중인 스터디룸에 대한 권한 조회",
          description = "API를 사용하는 유저가 참여하고 있는 스터디룸들에서의 권한을 모두 조회합니다.")
  @GetMapping("/mypage")
  public List<UserStudyRoomDTO> getUsersUserStudyRooms() {
    return userStudyRoomService.getUserStudyRoomsByUser();
  }

  @PostMapping("/add/{id}")
  @Operation(summary = "스터디룸 참가", description = "해당 API를 사용하는 유저가 스터디룸에 참가시킵니다.")
  @UserStudyRoomAnnotation.Success(description = "성공적으로 참가함")
  @StudyRoomAnnotation.Failure
  public ResponseEntity<UserStudyRoomDTO> joinStudyRoom(
      @Parameter(description = "참가할 스터디룸의 ID", required = true) @PathVariable Long id) {
    UserStudyRoomDTO createdStudyRoom = userStudyRoomService.joinStudyRoom(id);
    return ResponseEntity.ok(createdStudyRoom);
  }

  @DeleteMapping("/quit/{id}")
  @Operation(summary = "스터디룸 탈퇴", description = "해당 API를 사용하는 유저가 스터디룸에서 탈퇴합니다.")
  @ApiResponse(responseCode = "204", description = "성공적으로 탈퇴함")
  @StudyRoomAnnotation.Failure
  @StudyRoomAnnotation.Failure(description = "유저가 이미 해당 스터디룸에 존재하지 않음")
  public ResponseEntity<Void> quitStudyRoom(
      @Parameter(description = "탈퇴할 스터디룸의 ID", required = true) @PathVariable Long id) {
    userStudyRoomService.quitStudyRoom(id);
    return ResponseEntity.noContent().build();
  }
}
