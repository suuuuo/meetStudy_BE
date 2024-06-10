package com.elice.meetstudy.domain.admin.service;

import com.elice.meetstudy.domain.studyroom.DTO.StudyRoomDTO;
import com.elice.meetstudy.domain.studyroom.exception.EntityNotFoundException;
import com.elice.meetstudy.domain.studyroom.mapper.StudyRoomMapper;
import com.elice.meetstudy.domain.studyroom.repository.StudyRoomRepository;
import com.elice.meetstudy.domain.studyroom.service.UserStudyRoomService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminStudyRoomService {

    private final StudyRoomRepository studyRoomRepository;

    private final StudyRoomMapper studyRoomMapper;

    private final UserStudyRoomService userStudyRoomService;

    // 모든 스터디룸 조회
    public List<StudyRoomDTO> getAllStudyRooms() {
        return studyRoomRepository
                .findAll()
                .stream()
                .map(studyRoomMapper::toStudyRoomDTO)
                .collect(Collectors.toList());
    }

    // id에 해당하는 스터디룸 조회
    public StudyRoomDTO getStudyRoomById(Long id) {
        return studyRoomRepository
                .findById(id)
                .map(studyRoomMapper::toStudyRoomDTO)
                .orElseThrow(() -> new EntityNotFoundException("해당 id의 StudyRoom을 찾을 수 없습니다. [ID: " + id + "]"));
    }

    // 이메일에 해당하는 회원이 참여한 스터디룸 조회
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

    // id에 해당하는 스터디룸 삭제
    public void deleteStudyRoom(Long id) {
        studyRoomRepository.deleteById(id);
    }
}