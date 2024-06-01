package com.elice.meetstudy.domain.calendar.holiday.controller;

import com.elice.meetstudy.domain.calendar.holiday.service.HolidayService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import java.io.IOException;

@Controller
public class HolidayController {

   private final HolidayService holidayService;

    public HolidayController(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    @Scheduled(cron = "0 0 0 8 1 ?")
    public void getHolidays() throws IOException {
        holidayService.getHolidays();
    }
}


