package com.elice.meetstudy.domain.admin.controller;

import com.elice.meetstudy.domain.admin.service.AdminStudyRoomService;
import com.elice.meetstudy.domain.studyroom.DTO.StudyRoomDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/studyrooms")
@Tag(name = "C. 관리자")
public class AdminStudyRoomController {

    private final AdminStudyRoomService adminStudyRoomService;

    public AdminStudyRoomController(AdminStudyRoomService adminStudyRoomService) {
        this.adminStudyRoomService = adminStudyRoomService;
    }

    @Operation(summary = "모든 스터디룸 조회")
    @GetMapping
    public List<StudyRoomDTO> getAllStudyRooms() {
        return adminStudyRoomService.getAllStudyRooms();
    }

    @Operation(summary = "id에 해당하는 스터디룸 조회")
    @GetMapping("/{id}")
    public ResponseEntity<StudyRoomDTO> getStudyRoomById(
            @Parameter(description = "조회할 스터디룸의 ID", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(adminStudyRoomService.getStudyRoomById(id));
    }

    @Operation(summary = "특정 회원이 참가한 스터디룸 조회")
    @GetMapping("/user/{email}")
    public List<StudyRoomDTO> getStudyRoomByUserEmail(
            @Parameter(description = "참여한 스터디룸을 조회할 유저의 이메일", required = true) @PathVariable String email) {
        return adminStudyRoomService.getStudyRoomByEmail(email);
    }

    @Operation(summary = "스터디룸 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudyRoom(
            @Parameter(description = "삭제할 스터디룸의 ID", required = true) @PathVariable Long id) {
        adminStudyRoomService.deleteStudyRoom(id);
        return ResponseEntity.noContent().build();
    }
}