package com.elice.meetstudy.domain.calendar.dto;

import com.elice.meetstudy.domain.calendar.domain.Calendar_detail;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ResponseCalendarDetail{
    private final long id;
    private final String title;
    private final String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy.MM.dd HH:mm")
    private final LocalDateTime startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy.MM.dd HH:mm")
    private final LocalDateTime endDate;
    private final boolean isHoliday;

    public ResponseCalendarDetail(Calendar_detail calendarDetail, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = calendarDetail.getId();
        this.title = calendarDetail.getTitle() != null ? calendarDetail.getTitle() : "";
        this.content = calendarDetail.getContent() != null ? calendarDetail.getContent() : "";
        this.startDate =  startDate;
        this.endDate = endDate;
        this.isHoliday = calendarDetail.isHoliday();
    }
}


