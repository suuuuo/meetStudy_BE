package com.elice.meetstudy.domain.calendar.dto;

public record RequestCalendarDetail(
    long id,
    String title,
    String content,
    String startDay,
    String endDay,
    String startTime,
    String endTime
)
{}
