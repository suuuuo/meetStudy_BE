package com.elice.meetstudy.domain.studyroom.controller;

import com.elice.meetstudy.domain.studyroom.DTO.CreateStudyRoomDTO;
import com.elice.meetstudy.domain.studyroom.DTO.StudyRoomDTO;
import com.elice.meetstudy.domain.studyroom.DTO.UpdateStudyRoomDTO;
import com.elice.meetstudy.domain.studyroom.service.StudyRoomService;
import com.elice.meetstudy.domain.studyroom.service.UserStudyRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.RequestBody;
import java.net.URI;
import java.util.List;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/studyrooms")
@Tag(name = "I. 스터디룸", description = "스터디룸 관련 API 입니다.")
public class StudyRoomController {

  @Autowired private StudyRoomService studyRoomService;

  @Autowired private UserStudyRoomService userStudyRoomService;

  @Operation(summary = "모든 스터디룸 조회", description = "모든 스터디룸을 조회합니다. API 사용자의 관심사를 우선순위로 가집니다.")
  @StudyRoomAnnotation.Success(description = "성공적으로 조회됨")
  @GetMapping
  public List<StudyRoomDTO> getAllStudyRooms() {
    return studyRoomService.getAllStudyRooms();
  }

  @Operation(summary = "스터디룸 조회", description = "ID를 사용하여 특정 스터디룸을 조회합니다.")
  @StudyRoomAnnotation.Success(description = "성공적으로 조회됨")
  @StudyRoomAnnotation.Failure
  @GetMapping("/{id}")
  public ResponseEntity<StudyRoomDTO> getStudyRoomById(
      @Parameter(description = "조회할 스터디룸의 ID", required = true) @PathVariable Long id) {
    StudyRoomDTO studyRoomDTO = studyRoomService.getStudyRoomById(id);
    return ResponseEntity.ok(studyRoomDTO);
  }

  @Operation(summary = "참가 스터디룸 조회", description = "유저의 이메일을 사용하여, 해당 유저가 참가중인 스터디룸들을 조회합니다.")
  @StudyRoomAnnotation.Success(description = "성공적으로 조회됨")
  @StudyRoomAnnotation.Failure
  @GetMapping("/user/{email}")
  public List<StudyRoomDTO> getStudyRoomByUserEmail(
      @Parameter(description = "참여한 스터디룸을 조회할 유저의 이메일", required = true) @PathVariable
          String email) {
    return studyRoomService.getStudyRoomByEmail(email);
  }

  @Operation(
      summary = "스터디룸 생성",
      description = "새로운 스터디룸을 생성합니다. 해당 API를 사용하는 유저는 그 방의 OWNER 권한을 가집니다.")
  @StudyRoomAnnotation.Success(description = "성공적으로 생성됨")
  @PostMapping("/add")
  public ResponseEntity<StudyRoomDTO> createStudyRoom(
          @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "생성할 스터디룸 정보 JSON", required = true)
          @Valid @RequestBody
          CreateStudyRoomDTO createStudyRoomDTO) {
    StudyRoomDTO createdStudyRoom = studyRoomService.createStudyRoom(createStudyRoomDTO);
    URI location = URI.create(String.format("/api/studyrooms/%s", createdStudyRoom.getId()));
    return ResponseEntity.created(location).body(createdStudyRoom);
  }

  @Operation(summary = "스터디룸 업데이트", description = "ID를 사용하여 스터디룸을 업데이트합니다.")
  @PutMapping("/{id}")
  @StudyRoomAnnotation.Success(description = "성공적으로 수정됨")
  @StudyRoomAnnotation.Failure
  public ResponseEntity<StudyRoomDTO> updateStudyRoom(
      @Parameter(description = "수정할 스터디룸의 ID", required = true) @PathVariable Long id,
      @Parameter(description = "수정할 스터디룸 정보 JSON", required = true) @Valid @RequestBody
      UpdateStudyRoomDTO updateStudyRoomDTO) {
    StudyRoomDTO updatedStudyRoom = studyRoomService.updateStudyRoom(id, updateStudyRoomDTO);
    return ResponseEntity.ok(updatedStudyRoom);
  }

  @Operation(summary = "스터디룸 삭제", description = "ID를 사용하여 스터디룸을 삭제합니다.")
  @DeleteMapping("/{id}")
  @ApiResponse(responseCode = "204", description = "성공적으로 삭제됨")
  @StudyRoomAnnotation.Failure
  public ResponseEntity<Void> deleteStudyRoom(
      @Parameter(description = "삭제할 스터디룸의 ID", required = true) @PathVariable Long id) {
    studyRoomService.deleteStudyRoom(id);
    return ResponseEntity.noContent().build();
  }
}
