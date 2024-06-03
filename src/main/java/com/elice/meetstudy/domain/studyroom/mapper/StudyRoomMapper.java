package com.elice.meetstudy.domain.studyroom.mapper;

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

    StudyRoom toStudyRoom(StudyRoomDTO studyRoomDTO);

    @Mapping(source="userStudyRoom.studyRoom.id", target= "studyRoomId")
    UserStudyRoomDTO toUserStudyRoomDTO(UserStudyRoom userStudyRoom);

    UserStudyRoom toUserStudyRoom(UserStudyRoomDTO userStudyRoomDTO);
}
