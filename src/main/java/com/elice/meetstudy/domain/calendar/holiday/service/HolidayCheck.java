package com.elice.meetstudy.domain.calendar.holiday.service;

import com.elice.meetstudy.domain.calendar.holiday.controller.HolidayController;
import com.elice.meetstudy.domain.calendar.holiday.repository.HolidayRepository;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import org.springframework.stereotype.Component;

@Component
public class HolidayCheck {

    private final HolidayRepository holidayRepository;
    private final HolidayController holidayController;

    public HolidayCheck(HolidayRepository holidayRepository, HolidayController holidayController) {
        this.holidayRepository = holidayRepository;
        this.holidayController = holidayController;
    }

    @PostConstruct
    public void checkAndDoSomething() throws IOException {
        if(holidayRepository.count() == 0){ //공휴일 db가 비어있으면
            holidayController.getHolidays();
        }
    }
}
