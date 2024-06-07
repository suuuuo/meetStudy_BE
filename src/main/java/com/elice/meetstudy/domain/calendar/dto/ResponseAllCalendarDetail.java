package com.elice.meetstudy.domain.calendar.dto;

import com.elice.meetstudy.domain.calendar.domain.Calendar;
import com.elice.meetstudy.domain.calendar.domain.Calendar_detail;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Objects;
import lombok.Getter;

@Getter
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseAllCalendarDetail{

    private final long id;
    private final String studyRoomId;
    private final String title;
    private final String content;
    private final String startDay;
    private final String endDay;
    private final String startTime;
    private final String endTime;
    private final boolean isHoliday;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseAllCalendarDetail that = (ResponseAllCalendarDetail) o;
        return Objects.equals(startDay, that.startDay) &&
            Objects.equals(title, that.title) &&
            isHoliday == that.isHoliday;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDay, title, isHoliday);
    }

    public ResponseAllCalendarDetail(Calendar_detail calendarDetail) {
        this.id = calendarDetail.getId();
        if (calendarDetail.getCalendar() != null
            && calendarDetail.getCalendar().getStudyRoom() != null) {
            this.studyRoomId = String.valueOf(calendarDetail.getCalendar().getStudyRoom().getId());
        } else {
            this.studyRoomId = ""; // null인 경우 처리
        }
        this.title = calendarDetail.getTitle() != null ? calendarDetail.getTitle() : "";
        this.content = calendarDetail.getContent() != null ? calendarDetail.getContent() : "";
        this.startDay = calendarDetail.getStartDay() != null ? calendarDetail.getStartDay() : "";
        this.endDay = calendarDetail.getEndDay() != null ? calendarDetail.getEndDay() : "";
        this.startTime = calendarDetail.getStartTime() != null ? calendarDetail.getStartTime() : "";
        this.endTime = calendarDetail.getEndTime() != null ? calendarDetail.getEndTime() : "";
        this.isHoliday = calendarDetail.isHoliday();
    }
}
