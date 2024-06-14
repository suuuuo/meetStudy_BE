package com.elice.meetstudy.domain.studyroom.service;

import com.elice.meetstudy.domain.calendar.repository.CalendarRepository;
import com.elice.meetstudy.domain.calendar.service.CalendarService;
import com.elice.meetstudy.domain.category.service.CategoryService;
import com.elice.meetstudy.domain.studyroom.DTO.CreateStudyRoomDTO;
import com.elice.meetstudy.domain.studyroom.DTO.StudyRoomDTO;
import com.elice.meetstudy.domain.studyroom.DTO.UpdateStudyRoomDTO;
import com.elice.meetstudy.domain.studyroom.DTO.UserStudyRoomDTO;
import com.elice.meetstudy.domain.studyroom.entity.StudyRoom;
import com.elice.meetstudy.domain.studyroom.entity.UserStudyRoom;
import com.elice.meetstudy.domain.studyroom.exception.CustomNotValidException;
import com.elice.meetstudy.domain.studyroom.exception.EntityNotFoundException;
import com.elice.meetstudy.domain.studyroom.exception.StudyRoomAuthenticationException;
import com.elice.meetstudy.domain.studyroom.mapper.StudyRoomMapper;
import com.elice.meetstudy.domain.studyroom.repository.StudyRoomRepository;
import com.elice.meetstudy.domain.studyroom.repository.UserStudyRoomRepository;
import com.elice.meetstudy.domain.user.domain.User;
import com.elice.meetstudy.domain.user.domain.UserPrinciple;
import com.elice.meetstudy.domain.user.dto.UserJoinDto;
import com.elice.meetstudy.domain.user.repository.UserRepository;
import com.elice.meetstudy.domain.user.service.UserService;
import com.elice.meetstudy.util.EntityFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudyRoomService {

    @Autowired
    private StudyRoomRepository studyRoomRepository;

    @Autowired
    private UserStudyRoomRepository userStudyRoomRepository;

    @Autowired
    private StudyRoomMapper studyRoomMapper;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private UserService userService;

    @Autowired
    private UserStudyRoomService userStudyRoomService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CalendarService calendarService;

    @Autowired
    private EntityFinder entityFinder;

    /**
     * 모든 스터디룸을 조회하여 스터디룸 DTO 리스트로 반환합니다.
     *
     * @return 모든 스터디룸의 StudyRoomDTO 객체 리스트
     */
    public List<StudyRoomDTO> getAllStudyRooms() {
        List<Long> category = entityFinder.getUser().getInterests()
            .stream()
            .map(interest -> interest.getCategory().getId())
            .collect(Collectors.toList());

        return studyRoomRepository
                .findAllByUserInterests(category)
                .stream()
                .map(studyRoomMapper::toStudyRoomDTO)
                .collect(Collectors.toList());
    }

    /**
     * 주어진 ID에 해당하는 스터디룸을 조회하여 StudyRoomDTO 객체로 반환합니다.
     *
     * @param id 조회할 스터디룸의 ID
     * @return 주어진 ID에 해당하는 StudyRoomDTO 객체를 포함한 Optional 객체,
     *         스터디룸이 존재하지 않으면 빈 Optional 객체
     */
    public StudyRoomDTO getStudyRoomById(Long id) {
        return studyRoomRepository
                .findById(id)
                .map(studyRoomMapper::toStudyRoomDTO)
                .orElseThrow(() -> new EntityNotFoundException("해당 id의 StudyRoom을 찾을 수 없습니다. [ID: " + id + "]"));
    }

    /**
     * 주어진 이메일에 해당하는 유저가 참여한 스터디룸을 조회하여 StudyRoomDTO 객체로 반환합니다.
     *
     * @param email 조회할 스터디룸의 ID
     * @return 주어진 ID에 해당하는 StudyRoomDTO 객체를 포함한 Optional 객체,
     *         스터디룸이 존재하지 않으면 빈 Optional 객체
     */
    public List<StudyRoomDTO> getStudyRoomByEmail(String email) {
        return userStudyRoomService
                .getUserStudyRoomsByEmail(email).stream()
                .map((usr) -> studyRoomMapper
                        .toStudyRoomDTO(
                                studyRoomRepository
                                        .findById(usr.getStudyRoomId())
                                        .orElse(null)
                        )
                )
                .collect(Collectors.toList());
    }

    /**
     * 새로운 스터디룸을 생성하고 저장한 후, 해당 스터디룸을 StudyRoomDTO 객체로 변환하여 반환합니다.
     *
     * @param studyRoomDTO 생성할 스터디룸의 정보를 담고 있는 StudyRoomDTO 객체
     * @return 생성되고 저장된 스터디룸의 StudyRoomDTO 객체
     */
    public StudyRoomDTO createStudyRoom(CreateStudyRoomDTO studyRoomDTO) {

        // 스터디룸 생성
        StudyRoom studyRoom = studyRoomMapper.toStudyRoom(studyRoomDTO);
        studyRoom.setCategory(categoryService.findCategory(studyRoomDTO.getCategoryId()));
        studyRoom.setCreatedDate(new Date());

        // 방 생성하는 유저 가져오기
        UserPrinciple userPrincipal = (UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = Long.valueOf(userPrincipal.getUserId());

        User user = userRepository
                .findById(userId) // 실제 유저 상세정보 DB에서 호출
                .orElseThrow(() -> new EntityNotFoundException("스터디룸 생성에 실패하였습니다. 존재하지 않는 유저입니다."));

        // 유저스터디룸 생성
        UserStudyRoom userStudyRoom = UserStudyRoom.builder()
                .joinDate(new Date())
                .permission("OWNER")
                .build();


        userStudyRoom.setUser(user);
        user.addUserStudyRoom(userStudyRoom);

        userStudyRoom.setStudyRoom(studyRoom);

        studyRoom.addUserStudyRoom(userStudyRoom);


        // 스터디룸 저장
        StudyRoom savedStudyRoom = studyRoomRepository.save(studyRoom);

        return studyRoomMapper.toStudyRoomDTO(savedStudyRoom);
    }



    /**
     * 주어진 ID에 해당하는 스터디룸을 업데이트하고, 업데이트된 스터디룸을 StudyRoomDTO 객체로 반환합니다.
     *
     * @param id 업데이트할 스터디룸의 ID
     * @param studyRoomDTO 업데이트할 스터디룸의 정보를 담고 있는 StudyRoomDTO 객체
     * @return 주어진 ID에 해당하는 업데이트된 StudyRoomDTO 객체를 포함한 Optional 객체,
     *         스터디룸이 존재하지 않으면 빈 Optional 객체
     */
    public StudyRoomDTO updateStudyRoom(Long id, UpdateStudyRoomDTO studyRoomDTO) {

        // 방 수정하는 유저 가져오기
        UserPrinciple userPrincipal = (UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = Long.valueOf(userPrincipal.getUserId());

        String title = studyRoomDTO.getTitle();
        String description = studyRoomDTO.getDescription();
        Long userCapacity = studyRoomDTO.getUserCapacity();
        Long categoryId = studyRoomDTO.getCategoryId();

        if (title == null && description == null && userCapacity == null && categoryId == null) {
            throw new CustomNotValidException("수정할 스터디룸 정보가 입력되지 않았습니다.");
        }

        return studyRoomRepository.findById(id)
                .map(existingStudyRoom -> {
                    if (title != null) existingStudyRoom.setTitle(title);
                    if (description != null) existingStudyRoom.setDescription(description);
                    if (userCapacity != null) existingStudyRoom.setUserCapacity(userCapacity);
                    if (categoryId != null) existingStudyRoom.setCategory(categoryService.findCategory(categoryId));
                    existingStudyRoom.getUserStudyRooms().stream()
                            .filter(usr -> usr.getUser().getId().equals(userId))
                            .findAny()
                            .ifPresent(user -> {
                                if (!user.getPermission().equals("OWNER"))
                                    throw new StudyRoomAuthenticationException("스터디룸의 방장만이 방을 수정할 수 있습니다.");
                            });
                    return studyRoomMapper.toStudyRoomDTO(studyRoomRepository.save(existingStudyRoom));
                })
                .orElseThrow(() -> new EntityNotFoundException("해당 id의 StudyRoom을 찾을 수 없습니다. [ID: " + id + "]"));
    }

    /**
     * 주어진 ID에 해당하는 스터디룸을 삭제합니다.
     *
     * @param id 삭제할 스터디룸의 ID
     */
    public void deleteStudyRoom(Long id) {

        // 방 수정하는 유저 가져오기
        UserPrinciple userPrincipal = (UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = Long.valueOf(userPrincipal.getUserId());

        StudyRoom studyRoom = studyRoomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 id의 StudyRoom을 찾을 수 없습니다. [ID: " + id + "]"));

        UserStudyRoom userStudyRoom = studyRoom.getUserStudyRooms().stream()
                .filter(usr -> Objects.equals(usr.getUser().getId(), userId))
                .filter(user -> user.getPermission().equals("OWNER"))
                .findAny()
                .orElseThrow(() -> new EntityNotFoundException("해당 방의 방장만이 방을 삭제할 수 있습니다. [RoomID: " + id + "]"));


        calendarService.deleteStudyCalendar(id);
        studyRoomRepository.deleteById(id);
    }
}
