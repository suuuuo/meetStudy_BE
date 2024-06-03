package com.elice.meetstudy.domain.studyroom.controller;

import com.elice.meetstudy.domain.studyroom.DTO.StudyRoomDTO;
import com.elice.meetstudy.domain.studyroom.DTO.UserStudyRoomDTO;
import com.elice.meetstudy.domain.studyroom.service.StudyRoomService;
import com.elice.meetstudy.domain.studyroom.service.UserStudyRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/userstudyrooms")
public class UserStudyRoomController {
    @Autowired
    private UserStudyRoomService userStudyRoomService;

    @GetMapping
    public List<UserStudyRoomDTO> getAllUserStudyRooms() {
        return userStudyRoomService.getAllUserStudyRooms();
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<UserStudyRoomDTO> joinStudyRoom(
            @PathVariable Long id
            , @RequestBody Map<Object, String> email) {
        UserStudyRoomDTO createdStudyRoom = userStudyRoomService.joinStudyRoom(id, email.get("email"));
        return ResponseEntity.ok(createdStudyRoom);
    }
}
