package com.elice.meetstudy.domain.studyroom.mapper;

import com.elice.meetstudy.domain.studyroom.DTO.StudyRoomDTO;
import com.elice.meetstudy.domain.studyroom.DTO.UserStudyRoomDTO;
import com.elice.meetstudy.domain.studyroom.entity.StudyRoom;
import com.elice.meetstudy.domain.studyroom.entity.UserStudyRoom;
import com.elice.meetstudy.domain.user.domain.User;
import com.elice.meetstudy.domain.user.dto.UserLoginDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-02T00:34:55+0900",
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

        studyRoomDTO.id( studyRoom.getId() );
        studyRoomDTO.title( studyRoom.getTitle() );
        studyRoomDTO.description( studyRoom.getDescription() );
        studyRoomDTO.createdDate( studyRoom.getCreatedDate() );
        studyRoomDTO.maxCapacity( studyRoom.getMaxCapacity() );
        studyRoomDTO.userStudyRooms( userStudyRoomListToUserStudyRoomDTOList( studyRoom.getUserStudyRooms() ) );

        return studyRoomDTO.build();
    }

    @Override
    public StudyRoom toStudyRoom(StudyRoomDTO studyRoomDTO) {
        if ( studyRoomDTO == null ) {
            return null;
        }

        StudyRoom studyRoom = new StudyRoom();

        studyRoom.setId( studyRoomDTO.getId() );
        studyRoom.setTitle( studyRoomDTO.getTitle() );
        studyRoom.setDescription( studyRoomDTO.getDescription() );
        studyRoom.setCreatedDate( studyRoomDTO.getCreatedDate() );
        studyRoom.setMaxCapacity( studyRoomDTO.getMaxCapacity() );
        studyRoom.setUserStudyRooms( userStudyRoomDTOListToUserStudyRoomList( studyRoomDTO.getUserStudyRooms() ) );

        return studyRoom;
    }

    @Override
    public UserStudyRoomDTO toUserStudyRoomDTO(UserStudyRoom userStudyRoom) {
        if ( userStudyRoom == null ) {
            return null;
        }

        UserStudyRoomDTO.UserStudyRoomDTOBuilder userStudyRoomDTO = UserStudyRoomDTO.builder();

        userStudyRoomDTO.id( userStudyRoom.getId() );
        userStudyRoomDTO.joinDate( userStudyRoom.getJoinDate() );
        userStudyRoomDTO.permission( userStudyRoom.getPermission() );
        userStudyRoomDTO.user( userToUserLoginDto( userStudyRoom.getUser() ) );

        return userStudyRoomDTO.build();
    }

    @Override
    public UserStudyRoom toUserStudyRoom(UserStudyRoomDTO userStudyRoomDTO) {
        if ( userStudyRoomDTO == null ) {
            return null;
        }

        UserStudyRoom.UserStudyRoomBuilder userStudyRoom = UserStudyRoom.builder();

        userStudyRoom.id( userStudyRoomDTO.getId() );
        userStudyRoom.joinDate( userStudyRoomDTO.getJoinDate() );
        userStudyRoom.permission( userStudyRoomDTO.getPermission() );
        userStudyRoom.user( userLoginDtoToUser( userStudyRoomDTO.getUser() ) );

        return userStudyRoom.build();
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

    protected UserLoginDto userToUserLoginDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserLoginDto userLoginDto = new UserLoginDto();

        userLoginDto.setEmail( user.getEmail() );
        userLoginDto.setPassword( user.getPassword() );

        return userLoginDto;
    }

    protected User userLoginDtoToUser(UserLoginDto userLoginDto) {
        if ( userLoginDto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.email( userLoginDto.getEmail() );
        user.password( userLoginDto.getPassword() );

        return user.build();
    }
}
