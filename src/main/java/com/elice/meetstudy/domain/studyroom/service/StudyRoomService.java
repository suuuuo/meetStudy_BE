package com.elice.meetstudy.domain.studyroom.service;

import com.elice.meetstudy.domain.studyroom.DTO.StudyRoomDTO;
import com.elice.meetstudy.domain.studyroom.DTO.UserStudyRoomDTO;
import com.elice.meetstudy.domain.studyroom.entity.StudyRoom;
import com.elice.meetstudy.domain.studyroom.entity.UserStudyRoom;
import com.elice.meetstudy.domain.studyroom.mapper.StudyRoomMapper;
import com.elice.meetstudy.domain.studyroom.repository.StudyRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudyRoomService {

    @Autowired
    private StudyRoomRepository studyRoomRepository;

    @Autowired
    private StudyRoomMapper studyRoomMapper;

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
    public Optional<StudyRoomDTO> getStudyRoomById(Long id) {
        return studyRoomRepository
                .findById(id)
                .map(studyRoomMapper::toStudyRoomDTO);
    }

    /**
     * 새로운 스터디룸을 생성하고 저장한 후, 해당 스터디룸을 StudyRoomDTO 객체로 변환하여 반환합니다.
     *
     * @param studyRoomDTO 생성할 스터디룸의 정보를 담고 있는 StudyRoomDTO 객체
     * @return 생성되고 저장된 스터디룸의 StudyRoomDTO 객체
     */
    public StudyRoomDTO createStudyRoom(StudyRoomDTO studyRoomDTO) {
        StudyRoom studyRoom = studyRoomMapper.toStudyRoom(studyRoomDTO);
        studyRoom.setCreatedDate(new Date());
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
    public Optional<StudyRoomDTO> updateStudyRoom(Long id, StudyRoomDTO studyRoomDTO) {
        return studyRoomRepository.findById(id)
                .map(existingStudyRoom -> {
                    existingStudyRoom.setTitle(studyRoomDTO.getTitle());
                    existingStudyRoom.setDescription(studyRoomDTO.getDescription());
                    existingStudyRoom.setMaxCapacity(studyRoomDTO.getMaxCapacity());
                    return studyRoomMapper.toStudyRoomDTO(studyRoomRepository.save(existingStudyRoom));
                });
    }

    /**
     * 주어진 ID에 해당하는 스터디룸을 삭제합니다.
     *
     * @param id 삭제할 스터디룸의 ID
     */
    public void deleteStudyRoom(Long id) {
        studyRoomRepository.deleteById(id);
    }

    public UserStudyRoom convertToUserStudyRoomEntity(UserStudyRoomDTO userStudyRoomDTO) {
        return studyRoomMapper.toUserStudyRoom(userStudyRoomDTO);
    }

    public UserStudyRoomDTO convertToUserStudyRoomDTO(UserStudyRoom userStudyRoom) {
        return studyRoomMapper.toUserStudyRoomDTO(userStudyRoom);
    }
}
