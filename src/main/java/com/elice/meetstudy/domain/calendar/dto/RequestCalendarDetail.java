package com.elice.meetstudy.domain.calendar.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record RequestCalendarDetail(

    @NotEmpty(message = "일정 이름을 입력해주세요")
    @NotBlank(message = "일정 이름을 입력해주세요")
    String title,

    String content,
    @NotEmpty
    @NotBlank
    String startDay,
    @NotEmpty
    @NotBlank
    String endDay,
    @NotEmpty
    @NotBlank
    String startTime,
    @NotEmpty
    @NotBlank
    String endTime
)
{}
