package com.elice.meetstudy.domain.calendar.repository;

import com.elice.meetstudy.domain.calendar.domain.Calendar;
import com.elice.meetstudy.domain.calendar.domain.Calendar_detail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarDetailRepository extends JpaRepository<Calendar_detail, Long> {

    boolean existsByStartDay(String startDay);

    List<Calendar_detail> findAllByCalendar(Calendar calendar);

}