package com.elice.meetstudy.domain.calendar.holiday.service;

import com.elice.meetstudy.domain.calendar.domain.Calendar_detail;
import com.elice.meetstudy.domain.calendar.holiday.domain.Holiday;
import com.elice.meetstudy.domain.calendar.holiday.repository.HolidayRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HolidayService {

    @Autowired private HolidayRepository holidayRepository;

    @Transactional
    public void addHoliday(String date, String name){
        boolean isExist = holidayRepository.existsByDate(date);
        if (isExist) {
            System.out.println("이미 존재하는 공휴일입니다. 저장을 건너뜁니다.");
        } else{
            Holiday holiday = new Holiday(date, name);
            holidayRepository.save(holiday);
            System.out.println(name + "(" +date+")이(가) 저장되었습니다");
        }
    }

    // 연도, 달로 공휴일 리스트 뽑아서 전달 (일정 등록하기 위함)
    @Transactional
    public List<Holiday> Holiday(int year, int month){
        //LocalDate now = LocalDate.now();
        String Year = Integer.toString(year);

        String Month = Integer.toString(month);
        if(month < 10) Month = "0" + Month;

        String date = Year + Month;

        return holidayRepository.findByDateStartingWith(date);
    }
}

