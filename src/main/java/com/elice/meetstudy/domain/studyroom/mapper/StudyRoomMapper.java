package com.elice.meetstudy.domain.studyroom.mapper;

import com.elice.meetstudy.domain.studyroom.DTO.CreateStudyRoomDTO;
import com.elice.meetstudy.domain.studyroom.DTO.StudyRoomDTO;
import com.elice.meetstudy.domain.studyroom.DTO.UserStudyRoomDTO;
import com.elice.meetstudy.domain.studyroom.entity.StudyRoom;
import com.elice.meetstudy.domain.studyroom.entity.UserStudyRoom;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StudyRoomMapper {
    StudyRoomMapper INSTANCE = Mappers.getMapper(StudyRoomMapper.class);
    StudyRoomDTO toStudyRoomDTO(StudyRoom studyRoom);

    StudyRoomDTO toFindStudyRoomDTO(StudyRoom studyRoom);

    StudyRoom toStudyRoom(StudyRoomDTO studyRoomDTO);

    StudyRoom toStudyRoom(CreateStudyRoomDTO createStudyRoomDTO);

    @Mapping(source="userStudyRoom.studyRoom.id", target= "studyRoomId")
    UserStudyRoomDTO toUserStudyRoomDTO(UserStudyRoom userStudyRoom);

    UserStudyRoom toUserStudyRoom(UserStudyRoomDTO userStudyRoomDTO);
}
