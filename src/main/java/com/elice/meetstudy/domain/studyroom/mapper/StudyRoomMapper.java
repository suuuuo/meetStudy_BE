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


    @Mapping(source = "userStudyRooms", target = "userStudyRooms")
    StudyRoomDTO toStudyRoomDTO(StudyRoom studyRoom);

    @Mapping(source = "userStudyRooms", target = "userStudyRooms")
    StudyRoom toStudyRoom(StudyRoomDTO studyRoomDTO);

    @Mapping(source = "studyRoom", target = "studyRoom")
    UserStudyRoomDTO toUserStudyRoomDTO(UserStudyRoom userStudyRoom);

    @Mapping(source = "studyRoom", target = "studyRoom")
    UserStudyRoom toUserStudyRoom(UserStudyRoomDTO userStudyRoomDTO);
}
