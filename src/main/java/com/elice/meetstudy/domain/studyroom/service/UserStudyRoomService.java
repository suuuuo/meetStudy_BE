package com.elice.meetstudy.domain.studyroom.service;

import com.elice.meetstudy.domain.calendar.service.CalendarService;
import com.elice.meetstudy.domain.studyroom.DTO.UserStudyRoomDTO;
import com.elice.meetstudy.domain.studyroom.entity.StudyRoom;
import com.elice.meetstudy.domain.studyroom.entity.UserStudyRoom;
import com.elice.meetstudy.domain.studyroom.exception.EntityNotFoundException;
import com.elice.meetstudy.domain.studyroom.mapper.StudyRoomMapper;
import com.elice.meetstudy.domain.studyroom.repository.StudyRoomRepository;
import com.elice.meetstudy.domain.studyroom.repository.UserStudyRoomRepository;
import com.elice.meetstudy.domain.user.domain.User;
import com.elice.meetstudy.domain.user.domain.UserPrinciple;
import com.elice.meetstudy.domain.user.dto.UserJoinDto;
import com.elice.meetstudy.domain.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserStudyRoomService {

    private static final Logger logger = LoggerFactory.getLogger(UserStudyRoomService.class);
    @Autowired
    private UserStudyRoomRepository userStudyRoomRepository;

    @Autowired
    private StudyRoomRepository studyRoomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudyRoomMapper studyRoomMapper;

    @Autowired
    private CalendarService calendarService;


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
                .orElseThrow(() -> new EntityNotFoundException("해당 email의 User를 찾을 수 없습니다. [Email: " + email + "]"));

        return userStudyRoomRepository
                .findByUser(user)
                .stream()
                .map(studyRoomMapper::toUserStudyRoomDTO)
                .collect(Collectors.toList());
    }

    /**
     * API를 사용하는 유저의 스터디룸 소속 정보를 조회하여 유저스터디룸 DTO 리스트로 반환합니다.
     *
     * @return 모든 유저의 UserStudyRoomDTO 객체 리스트
     */
    public List<UserStudyRoomDTO> getUserStudyRoomsByUser() {
        // 유저 가져오기
        UserPrinciple userPrincipal = (UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = Long.valueOf(userPrincipal.getUserId());

        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당 User를 찾을 수 없습니다. [Email: " + userId + "]"));

        return userStudyRoomRepository
                .findByUser(user)
                .stream()
                .map(studyRoomMapper::toUserStudyRoomDTO)
                .collect(Collectors.toList());
    }

    /**
     * 주어진 스터디룸 ID에 유저를 참가시킨 뒤, 해당 스터디룸을 UserStudyRoomDTO 객체로 변환하여 반환합니다.
     *
     * @param studyRoomId 참가할 스터디룸 ID
     * @return 저장된 스터디룸의 UserStudyRoomDTO 객체
     */
    public UserStudyRoomDTO joinStudyRoom(Long studyRoomId) {
        // 방 참가하는 유저 가져오기
        UserPrinciple userPrincipal = (UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = Long.valueOf(userPrincipal.getUserId());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id의 User를 찾을 수 없습니다. [Email: " + userId + "]"));

        StudyRoom studyRoom = studyRoomRepository.findById(studyRoomId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id의 StudyRoom을 찾을 수 없습니다. [ID: " + studyRoomId + "]"));

        studyRoom.getUserStudyRooms().stream()
                .filter((usr) -> Objects.equals(usr.getUser().getId(), userId))
                .findAny()
                .ifPresent(e -> {
                    throw new EntityNotFoundException("이미 해당 유저가 존재합니다. [RoomID: " + studyRoomId + ", Email: email]");
                });

        UserStudyRoom userStudyRoom = new UserStudyRoom();
        userStudyRoom.setUser(user);
        userStudyRoom.setStudyRoom(studyRoom);
        userStudyRoom.setJoinDate(new Date());
        userStudyRoom.setPermission("MEMBER");

        UserStudyRoom savedUserStudyRoom = userStudyRoomRepository.save(userStudyRoom);

        return studyRoomMapper.toUserStudyRoomDTO(savedUserStudyRoom);
    }

    public void quitStudyRoom(Long id) {
        // 방 퇴장하는 유저 가져오기
        UserPrinciple userPrincipal = (UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = Long.valueOf(userPrincipal.getUserId());

        StudyRoom studyRoom = studyRoomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 id의 StudyRoom을 찾을 수 없습니다. [ID: " + id + "]"));

        UserStudyRoom userStudyRoom = studyRoom.getUserStudyRooms().stream()
                .filter((usr) -> Objects.equals(usr.getUser().getId(), userId))
                .findAny()
                .orElseThrow(() -> new EntityNotFoundException("유저가 이미 해당 스터디룸에 존재하지 않습니다. [RoomID: " + id + ", UserID: " + userId + "]"));


        studyRoom.getUserStudyRooms().remove(userStudyRoom);

        // OWNER가 나갔다면 남은 유저중 가장 먼저 가입한 유저가 Owner 계승
        //                남은 유저가 없다면 Studyroom 삭제
        if (userStudyRoom.getPermission().equals("OWNER")) {
            studyRoom.getUserStudyRooms().stream()
                    .min(Comparator.comparing(UserStudyRoom::getJoinDate))
                    .ifPresentOrElse(
                            user -> {
                                user.setPermission("OWNER");
                                studyRoomRepository.save(studyRoom);
                            },
                            () -> {
                                calendarService.deleteStudyCalendar(id);
                                studyRoomRepository.deleteById(id);
                            });
        } else {
            studyRoomRepository.save(studyRoom);
        }
    }

    public UserStudyRoomDTO createUserStudyRoom(UserStudyRoomDTO userStudyRoomDTO) {
        UserStudyRoom userStudyRoom = studyRoomMapper.toUserStudyRoom(userStudyRoomDTO);
        userStudyRoom.setJoinDate(new Date());
        UserStudyRoom savedUserStudyRoom = userStudyRoomRepository.save(userStudyRoom);
        return studyRoomMapper.toUserStudyRoomDTO(savedUserStudyRoom);
    }


}
