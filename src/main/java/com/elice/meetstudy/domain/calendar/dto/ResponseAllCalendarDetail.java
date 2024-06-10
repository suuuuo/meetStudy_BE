package com.elice.meetstudy.domain.calendar.dto;

import com.elice.meetstudy.domain.calendar.domain.Calendar;
import com.elice.meetstudy.domain.calendar.domain.Calendar_detail;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Getter;

@Getter
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseAllCalendarDetail{

    private final long id;
    private final String studyRoomId;
    private final String title;
    private final String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy.MM.dd HH:mm")
    private final LocalDateTime startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy.MM.dd HH:mm")
    private final LocalDateTime endDate;
    private final boolean isHoliday;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseAllCalendarDetail that = (ResponseAllCalendarDetail) o;
        return Objects.equals(startDate, that.startDate) &&
            Objects.equals(title, that.title) &&
            isHoliday == that.isHoliday;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, title, isHoliday);
    }

    public ResponseAllCalendarDetail(Calendar_detail calendarDetail, LocalDateTime startDate,
        LocalDateTime endDate) {
        this.id = calendarDetail.getId();
        if (calendarDetail.getCalendar() != null
            && calendarDetail.getCalendar().getStudyRoom() != null) {
            this.studyRoomId = String.valueOf(calendarDetail.getCalendar().getStudyRoom().getId());
        } else {
            this.studyRoomId = ""; // null인 경우 처리
        }
        this.title = calendarDetail.getTitle() != null ? calendarDetail.getTitle() : "";
        this.content = calendarDetail.getContent() != null ? calendarDetail.getContent() : "";
        this.startDate =  startDate;
        this.endDate = endDate;
        this.isHoliday = calendarDetail.isHoliday();
    }
}
