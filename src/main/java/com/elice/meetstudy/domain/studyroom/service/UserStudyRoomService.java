package com.elice.meetstudy.domain.studyroom.service;

import com.elice.meetstudy.domain.studyroom.DTO.StudyRoomDTO;
import com.elice.meetstudy.domain.studyroom.DTO.UserStudyRoomDTO;
import com.elice.meetstudy.domain.studyroom.entity.StudyRoom;
import com.elice.meetstudy.domain.studyroom.entity.UserStudyRoom;
import com.elice.meetstudy.domain.studyroom.repository.StudyRoomRepository;
import com.elice.meetstudy.domain.studyroom.repository.UserStudyRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserStudyRoomService {

    @Autowired
    private UserStudyRoomRepository userStudyRoomRepository;

    @Autowired
    private StudyRoomService studyRoomService;

    public UserStudyRoomDTO createUserStudyRoom(UserStudyRoomDTO userStudyRoomDTO) {
        UserStudyRoom userStudyRoom = studyRoomService.convertToUserStudyRoomEntity(userStudyRoomDTO);
        userStudyRoom.setJoinDate(new Date());
        UserStudyRoom savedUserStudyRoom = userStudyRoomRepository.save(userStudyRoom);
        return studyRoomService.convertToUserStudyRoomDTO(savedUserStudyRoom);
    }
}
