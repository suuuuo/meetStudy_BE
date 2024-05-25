package com.elice.meetstudy.domain.studyroom.service;

import com.elice.meetstudy.domain.studyroom.DTO.StudyRoomDTO;
import com.elice.meetstudy.domain.studyroom.entity.StudyRoom;
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

    // 모든 Study Room을 탐색 후 DTO 리스트로 반환합니다.
    public List<StudyRoomDTO> getAllStudyRooms() {
        return studyRoomRepository
                .findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ID로 스터디룸을 탐색합니다.
    public Optional<StudyRoomDTO> getStudyRoomById(Long id) {
        return studyRoomRepository
                .findById(id)
                .map(this::convertToDTO);
    }

    // 새로운 스터디룸을 제작합니다. 중복검사 없음
    public StudyRoomDTO createStudyRoom(StudyRoomDTO studyRoomDTO) {
        StudyRoom studyRoom = convertToEntity(studyRoomDTO);
        studyRoom.setCreatedDate(new Date());
        StudyRoom savedStudyRoom = studyRoomRepository.save(studyRoom);
        return convertToDTO(savedStudyRoom);
    }

    // ID를 기준으로 스터디룸을 업데이트합니다.
    public Optional<StudyRoomDTO> updateStudyRoom(Long id, StudyRoomDTO studyRoomDTO) {
        return studyRoomRepository.findById(id)
                .map(existingStudyRoom -> {
                    existingStudyRoom.setTitle(studyRoomDTO.getTitle());
                    existingStudyRoom.setDescription(studyRoomDTO.getDescription());
                    existingStudyRoom.setMaxCapacity(studyRoomDTO.getMaxCapacity());
                    return convertToDTO(studyRoomRepository.save(existingStudyRoom));
                });
    }

    // 스터디룸을 삭제합니다.
    public void deleteStudyRoom(Long id) {
        studyRoomRepository.deleteById(id);
    }

    // mapper를 추가하기전 구현부를 위한 임시적인 함수입니다.
    // 스터디룸 Entity를 DTO로 변환합니다.
    private StudyRoomDTO convertToDTO(StudyRoom studyRoom) {
        return new StudyRoomDTO(
                studyRoom.getId(),
                studyRoom.getTitle(),
                studyRoom.getDescription(),
                studyRoom.getCreatedDate(),
                studyRoom.getMaxCapacity()
        );
    }

    // 스터디룸 DTO를 Entity로 변환합니다.
    private StudyRoom convertToEntity(StudyRoomDTO studyRoomDTO) {
        return new StudyRoom(
                studyRoomDTO.getId(),
                studyRoomDTO.getTitle(),
                studyRoomDTO.getDescription(),
                studyRoomDTO.getCreatedDate(),
                studyRoomDTO.getMaxCapacity()
        );
    }
}
