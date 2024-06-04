package com.elice.meetstudy.domain.calendar.dto;

import jakarta.validation.constraints.NotBlank;

public record RequestCalendarDetail(
    @NotBlank(message = "일정 이름을 입력해주세요")
    String title,
    String content,
    @NotBlank(message = "시작 날짜를 입력해주세요")
    String startDay,
    String endDay,
    String startTime,
    String endTime
)
{}
