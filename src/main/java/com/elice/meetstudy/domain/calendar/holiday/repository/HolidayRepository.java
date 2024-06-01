package com.elice.meetstudy.domain.calendar.holiday.repository;

import com.elice.meetstudy.domain.calendar.holiday.domain.Holiday;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long> {
    boolean existsByDate(String date);
    List<Holiday> findByDateStartingWith(String datePrefix);
}
