package com.elice.meetstudy.domain.studyroom.repository;

import com.elice.meetstudy.domain.studyroom.entity.UserStudyRoom;
import com.elice.meetstudy.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserStudyRoomRepository extends JpaRepository<UserStudyRoom, Long> {

    List<UserStudyRoom> findByUser(User user);
}
