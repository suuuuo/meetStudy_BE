package com.elice.meetstudy.domain.studyroom.service;

import com.elice.meetstudy.domain.studyroom.DTO.UserStudyRoomDTO;
import com.elice.meetstudy.domain.studyroom.entity.UserStudyRoom;
import com.elice.meetstudy.domain.studyroom.exception.EntityNotFoundException;
import com.elice.meetstudy.domain.studyroom.mapper.StudyRoomMapper;
import com.elice.meetstudy.domain.studyroom.repository.UserStudyRoomRepository;
import com.elice.meetstudy.domain.user.domain.User;
import com.elice.meetstudy.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserStudyRoomService {

    @Autowired
    private UserStudyRoomRepository userStudyRoomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudyRoomMapper studyRoomMapper;


    /**
     * 모든 유저의 스터디룸 소속 정보를 조회하여 유저스터디룸 DTO 리스트로 반환합니다.
     * 해당 메소드는 구현 여부 테스트용 메소드이므로 사용을 자제해주시기 바랍니다.
     *
     * @return 모든 유저의 UserStudyRoomDTO 객체 리스트
     */
    public List<UserStudyRoomDTO> getAllUserStudyRooms() {
        return userStudyRoomRepository
                .findAll()
                .stream()
                .map(studyRoomMapper::toUserStudyRoomDTO)
                .collect(Collectors.toList());
    }

    /**
     * 주어진 이메일에 해당하는 유저의 스터디룸 소속 정보를 조회하여 유저스터디룸 DTO 리스트로 반환합니다.
     *
     * @return 모든 유저의 UserStudyRoomDTO 객체 리스트
     */
    public List<UserStudyRoomDTO> getUserStudyRoomsByEmail(String email) {
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("해당 email의 User를 찾을 수 없습니다. [Email :" + email + "]"));

        return userStudyRoomRepository
                .findByUser(user)
                .stream()
                .map(studyRoomMapper::toUserStudyRoomDTO)
                .collect(Collectors.toList());
    }

    public UserStudyRoomDTO createUserStudyRoom(UserStudyRoomDTO userStudyRoomDTO) {
        UserStudyRoom userStudyRoom = studyRoomMapper.toUserStudyRoom(userStudyRoomDTO);
        userStudyRoom.setJoinDate(new Date());
        UserStudyRoom savedUserStudyRoom = userStudyRoomRepository.save(userStudyRoom);
        return studyRoomMapper.toUserStudyRoomDTO(savedUserStudyRoom);
    }
}
