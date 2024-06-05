package com.elice.meetstudy.domain.studyroom.service;

import com.elice.meetstudy.domain.studyroom.DTO.StudyRoomDTO;
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
import com.elice.meetstudy.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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

    /**
     * 모든 스터디룸을 조회하여 스터디룸 DTO 리스트로 반환합니다.
     *
     * @return 모든 스터디룸의 StudyRoomDTO 객체 리스트
     */
    public List<StudyRoomDTO> getAllStudyRooms() {
        return studyRoomRepository
                .findAll()
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
    public StudyRoomDTO createStudyRoom(StudyRoomDTO studyRoomDTO) {

        // 스터디룸 생성
        StudyRoom studyRoom = studyRoomMapper.toStudyRoom(studyRoomDTO);
        studyRoom.setCreatedDate(new Date());

        // 방 생성하는 유저 가져오기
        String userPrincipal = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = null;

        if (userPrincipal.equals("anonymousUser")) { // PostMan으로 테스트하여, 로그인세션 없이 anonymousUser인 경우
            UserJoinDto userJoinDto = new UserJoinDto(
                    "test@by.postman.com",
                    "TESTPASSWORD1024",
                    "홍길동",
                    "Test1024★",
                    new ArrayList<>()
            );

            user = userRepository
                    .findByEmail("test@by.postman.com")
                    .orElseGet(() -> userService.join(userJoinDto));

        } else {
            user = userRepository
                    .findByEmail(userPrincipal) // 실제 유저 상세정보 DB에서 호출
                    .orElseThrow(() -> new EntityNotFoundException("스터디룸 생성에 실패하였습니다. 존재하지 않는 유저입니다."));
        }

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
    public StudyRoomDTO updateStudyRoom(Long id, StudyRoomDTO studyRoomDTO) {
        return studyRoomRepository.findById(id)
                .map(existingStudyRoom -> {
                    existingStudyRoom.setTitle(studyRoomDTO.getTitle());
                    existingStudyRoom.setDescription(studyRoomDTO.getDescription());
                    existingStudyRoom.setMaxCapacity(studyRoomDTO.getMaxCapacity());
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
        studyRoomRepository.deleteById(id);
    }
}
