package com.elice.meetstudy.domain.calendar.holiday.service;

import com.elice.meetstudy.domain.calendar.holiday.controller.HolidayController;
import com.elice.meetstudy.domain.calendar.holiday.repository.HolidayRepository;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HolidayCheck {

    @Autowired private HolidayRepository holidayRepository;
    @Autowired private HolidayController holidayController;

    @PostConstruct
    public void checkAndDoSomething() throws IOException {
        if(holidayRepository.count() == 0){
            holidayController.getHoliday();
        }
    }
}
