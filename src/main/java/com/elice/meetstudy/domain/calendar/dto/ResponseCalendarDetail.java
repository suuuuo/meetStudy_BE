package com.elice.meetstudy.domain.calendar.dto;

public record ResponseCalendarDetail(
    String title,
    String content,
    String startDay,
    String endDay,
    String startTime,
    String endTime,
    boolean isHoliday
)
{}
