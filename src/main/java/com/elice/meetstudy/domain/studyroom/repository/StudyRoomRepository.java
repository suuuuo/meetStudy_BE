package com.elice.meetstudy.domain.studyroom.repository;

import com.elice.meetstudy.domain.studyroom.entity.StudyRoom;
import com.elice.meetstudy.domain.user.domain.Interest;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyRoomRepository extends JpaRepository<StudyRoom, Long> {


  @Query("select s from StudyRoom s "
          + "join fetch s.userStudyRooms us "
          + "where s.id = :id and us.user.id = :userId")
  Optional<StudyRoom> findStudyRoomByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);

  @Query("SELECT s FROM StudyRoom s " +
      "LEFT JOIN s.category c " +
      "ORDER BY CASE WHEN c.id IN :interests THEN 1 ELSE 2 END")
  List<StudyRoom> findAllByUserInterests(@Param("interests") List<Long> interests);

}
