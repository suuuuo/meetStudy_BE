package com.elice.meetstudy.domain.studyroom.controller;

import com.elice.meetstudy.domain.studyroom.DTO.StudyRoomDTO;
import com.elice.meetstudy.domain.studyroom.service.StudyRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/studyrooms")
public class StudyRoomController {

    @Autowired
    private StudyRoomService studyRoomService;

    @GetMapping
    public List<StudyRoomDTO> getAllStudyRooms() {
        return studyRoomService.getAllStudyRooms();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudyRoomDTO> getStudyRoomById(@PathVariable Long id) {
        Optional<StudyRoomDTO> studyRoomDTO = studyRoomService.getStudyRoomById(id);
        return studyRoomDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build()); // 글로벌 exception 두고 던지기
    }

    @PostMapping("/add")
    public ResponseEntity<StudyRoomDTO> createStudyRoom(@RequestBody StudyRoomDTO studyRoomDTO) {
        StudyRoomDTO createdStudyRoom = studyRoomService.createStudyRoom(studyRoomDTO);
        return ResponseEntity.ok(createdStudyRoom);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudyRoomDTO> updateStudyRoom(@PathVariable Long id, @RequestBody StudyRoomDTO studyRoomDTO) {
        Optional<StudyRoomDTO> updatedStudyRoom = studyRoomService.updateStudyRoom(id, studyRoomDTO);
        return updatedStudyRoom.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudyRoom(@PathVariable Long id) {
        studyRoomService.deleteStudyRoom(id);
        return ResponseEntity.noContent().build();
    }
}
