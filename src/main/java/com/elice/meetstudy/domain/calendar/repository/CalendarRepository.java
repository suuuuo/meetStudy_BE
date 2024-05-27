package com.elice.meetstudy.domain.calendar.repository;

import com.elice.meetstudy.domain.calendar.domain.Calendar;
import com.elice.meetstudy.domain.calendar.domain.Calendar_detail;
import java.util.List;
import java.util.Optional;
import org.mapstruct.ap.shaded.freemarker.template.utility.OptimizerUtil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface
CalendarRepository extends JpaRepository<Calendar, Long> {

    //roomId가 없는, 개인 캘린더 찾기 : 스터디룸 아이디 없는 조건 추가해야함
    Optional<Calendar> findByUserId(Long userId);

    //roomId 있는, 공용 캘린더 찾기
    Optional<Calendar> findByStudyRoomId(Long studyRoomId);
}
