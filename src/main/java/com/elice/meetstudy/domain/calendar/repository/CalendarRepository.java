package com.elice.meetstudy.domain.calendar.repository;

import com.elice.meetstudy.domain.calendar.domain.Calendar;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface
CalendarRepository extends JpaRepository<Calendar, Long> {

    //roomId가 없는, 개인 캘린더 찾기
    Optional<Calendar> findByUserIdAndStudyRoomIsNull(Long userId);

    //roomId 있는, 공용 캘린더 찾기
    Optional<Calendar> findByStudyRoomId(Long studyRoomId);
}
