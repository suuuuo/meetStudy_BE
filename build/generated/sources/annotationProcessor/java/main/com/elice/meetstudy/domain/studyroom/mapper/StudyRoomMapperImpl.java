package com.elice.meetstudy.domain.studyroom.mapper;

import com.elice.meetstudy.domain.studyroom.DTO.StudyRoomDTO;
import com.elice.meetstudy.domain.studyroom.DTO.UserStudyRoomDTO;
import com.elice.meetstudy.domain.studyroom.entity.StudyRoom;
import com.elice.meetstudy.domain.studyroom.entity.UserStudyRoom;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-31T22:32:52+0900",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.7.jar, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class StudyRoomMapperImpl implements StudyRoomMapper {

    @Override
    public StudyRoomDTO toStudyRoomDTO(StudyRoom studyRoom) {
        if ( studyRoom == null ) {
            return null;
        }

        StudyRoomDTO.StudyRoomDTOBuilder studyRoomDTO = StudyRoomDTO.builder();

        studyRoomDTO.userStudyRooms( userStudyRoomListToUserStudyRoomDTOList( studyRoom.getUserStudyRooms() ) );
        studyRoomDTO.id( studyRoom.getId() );
        studyRoomDTO.title( studyRoom.getTitle() );
        studyRoomDTO.description( studyRoom.getDescription() );
        studyRoomDTO.createdDate( studyRoom.getCreatedDate() );
        studyRoomDTO.maxCapacity( studyRoom.getMaxCapacity() );

        return studyRoomDTO.build();
    }

    @Override
    public StudyRoom toStudyRoom(StudyRoomDTO studyRoomDTO) {
        if ( studyRoomDTO == null ) {
            return null;
        }

        StudyRoom studyRoom = new StudyRoom();

        studyRoom.setUserStudyRooms( userStudyRoomDTOListToUserStudyRoomList( studyRoomDTO.getUserStudyRooms() ) );
        studyRoom.setId( studyRoomDTO.getId() );
        studyRoom.setTitle( studyRoomDTO.getTitle() );
        studyRoom.setDescription( studyRoomDTO.getDescription() );
        studyRoom.setCreatedDate( studyRoomDTO.getCreatedDate() );
        studyRoom.setMaxCapacity( studyRoomDTO.getMaxCapacity() );

        return studyRoom;
    }

    @Override
    public UserStudyRoomDTO toUserStudyRoomDTO(UserStudyRoom userStudyRoom) {
        if ( userStudyRoom == null ) {
            return null;
        }

        UserStudyRoomDTO.UserStudyRoomDTOBuilder userStudyRoomDTO = UserStudyRoomDTO.builder();

        userStudyRoomDTO.studyRoom( toStudyRoomDTO( userStudyRoom.getStudyRoom() ) );
        userStudyRoomDTO.id( userStudyRoom.getId() );
        userStudyRoomDTO.joinDate( userStudyRoom.getJoinDate() );
        userStudyRoomDTO.permission( userStudyRoom.getPermission() );

        return userStudyRoomDTO.build();
    }

    @Override
    public UserStudyRoom toUserStudyRoom(UserStudyRoomDTO userStudyRoomDTO) {
        if ( userStudyRoomDTO == null ) {
            return null;
        }

        UserStudyRoom userStudyRoom = new UserStudyRoom();

        userStudyRoom.setStudyRoom( toStudyRoom( userStudyRoomDTO.getStudyRoom() ) );
        userStudyRoom.setId( userStudyRoomDTO.getId() );
        userStudyRoom.setJoinDate( userStudyRoomDTO.getJoinDate() );
        userStudyRoom.setPermission( userStudyRoomDTO.getPermission() );

        return userStudyRoom;
    }

    protected List<UserStudyRoomDTO> userStudyRoomListToUserStudyRoomDTOList(List<UserStudyRoom> list) {
        if ( list == null ) {
            return null;
        }

        List<UserStudyRoomDTO> list1 = new ArrayList<UserStudyRoomDTO>( list.size() );
        for ( UserStudyRoom userStudyRoom : list ) {
            list1.add( toUserStudyRoomDTO( userStudyRoom ) );
        }

        return list1;
    }

    protected List<UserStudyRoom> userStudyRoomDTOListToUserStudyRoomList(List<UserStudyRoomDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<UserStudyRoom> list1 = new ArrayList<UserStudyRoom>( list.size() );
        for ( UserStudyRoomDTO userStudyRoomDTO : list ) {
            list1.add( toUserStudyRoom( userStudyRoomDTO ) );
        }

        return list1;
    }
}
