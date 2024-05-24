package com.elice.meetstudy.domain.calendar.repository;

import com.elice.meetstudy.domain.calendar.domain.Calendar;
import com.elice.meetstudy.domain.calendar.domain.Calendar_detail;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Long> {

    Optional<Calendar> findByUserId(Long userId);

    //roomId가 없는, 개인 캘린더 찾기
    Calendar findByUserIdAndRoomIdIsNull(Long userId);

    //roomId 있는, 공용 캘린더 찾기
    Calendar findByRoomId(Long roomId);
}
