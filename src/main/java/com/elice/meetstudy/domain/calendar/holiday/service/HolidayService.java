package com.elice.meetstudy.domain.calendar.holiday.service;

import com.elice.meetstudy.domain.calendar.holiday.domain.Holiday;
import com.elice.meetstudy.domain.calendar.holiday.repository.HolidayRepository;
import java.util.List;
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
        } else{
            Holiday holiday = new Holiday(date, name);
            holidayRepository.save(holiday);
        }
    }

    // 연도, 달로 공휴일 리스트 뽑아서 전달 (일정 등록하기 위함)
    @Transactional
    public List<Holiday> Holiday(String year, String month){
        //LocalDate now = LocalDate.now();
        int Month = Integer.parseInt(month);
        if(Month < 10) month = "0" + month;

        String date = year + month;

        return holidayRepository.findByDateStartingWith(date);
    }
}

