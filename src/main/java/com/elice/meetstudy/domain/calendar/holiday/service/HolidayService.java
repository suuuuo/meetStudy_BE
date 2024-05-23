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

    // 일정의 날짜가 공휴일인지 확인
    public boolean checkHoliday(){
        LocalDate now = LocalDate.now();
        String year = Integer.toString(now.getYear());

        String month = Integer.toString(now.getMonthValue());
        if(now.getMonthValue() < 10) month = "0"+month;

        String day = Integer.toString(now.getDayOfMonth());
        if(now.getDayOfMonth() < 10) day = "0" + day;

        String date = year + month + day;

        System.out.println("year = " + year);
        System.out.println("month = " + month);
        System.out.println("day = " + day);
        System.out.println("date = " + date);

         Optional<Holiday> optionalHoliday = holidayRepository.findByDate(date);
        List<Holiday> holidayList = holidayRepository.findByDateStartingWith("202405");
        System.out.println("holidayList.size = " + holidayList.size());

         if(optionalHoliday.isPresent()){
             Holiday holiday = optionalHoliday.get();
             //일정의 isholiday true로 바꿔주기
             //일정의 날짜
             return true;
         }else{
             return false;
         }
    }
}
