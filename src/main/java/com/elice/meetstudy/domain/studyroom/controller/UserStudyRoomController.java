package com.elice.meetstudy.domain.studyroom.controller;

import com.elice.meetstudy.domain.studyroom.DTO.StudyRoomDTO;
import com.elice.meetstudy.domain.studyroom.DTO.UserStudyRoomDTO;
import com.elice.meetstudy.domain.studyroom.service.StudyRoomService;
import com.elice.meetstudy.domain.studyroom.service.UserStudyRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/userstudyrooms")
public class UserStudyRoomController {
    @Autowired
    private UserStudyRoomService userStudyRoomService;

    @GetMapping
    public List<UserStudyRoomDTO> getAllUserStudyRooms() {
        return userStudyRoomService.getAllUserStudyRooms();
    }
}
