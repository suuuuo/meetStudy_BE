package com.elice.meetstudy.domain.calendar.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

//@JsonInclude(JsonInclude.Include.NON_NULL)
public record ResponseCalendarDetail(
    long id,
    String title,
    String content,
    String startDay,
    String endDay,
    String startTime,
    String endTime,
    boolean isHoliday
)
{}
