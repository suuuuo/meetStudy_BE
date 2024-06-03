package com.elice.meetstudy.domain.studyroom.controller;

import com.elice.meetstudy.domain.studyroom.DTO.StudyRoomDTO;
import com.elice.meetstudy.domain.studyroom.service.StudyRoomService;
import com.elice.meetstudy.domain.user.dto.UserLoginDto;
import com.fasterxml.jackson.databind.node.TextNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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
        StudyRoomDTO studyRoomDTO = studyRoomService.getStudyRoomById(id);
        return ResponseEntity.ok(studyRoomDTO);
    }

    @GetMapping("/user")
    public List<StudyRoomDTO> getStudyRoomByUserEmail(@RequestBody Map<Object, String> email) {
        return studyRoomService.getStudyRoomByEmail(email.get("email"));
    }


    @PostMapping("/add")
    public ResponseEntity<StudyRoomDTO> createStudyRoom(@RequestBody StudyRoomDTO studyRoomDTO) {
        StudyRoomDTO createdStudyRoom = studyRoomService.createStudyRoom(studyRoomDTO);
        return ResponseEntity.ok(createdStudyRoom);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudyRoomDTO> updateStudyRoom(@PathVariable Long id, @RequestBody StudyRoomDTO studyRoomDTO) {
        StudyRoomDTO updatedStudyRoom = studyRoomService.updateStudyRoom(id, studyRoomDTO);
        return ResponseEntity.ok(updatedStudyRoom);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudyRoom(@PathVariable Long id) {
        studyRoomService.deleteStudyRoom(id);
        return ResponseEntity.noContent().build();
    }
}
