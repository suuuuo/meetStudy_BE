package com.elice.meetstudy.domain.calendar.mapper;

import com.elice.meetstudy.domain.calendar.domain.Calendar_detail;
import com.elice.meetstudy.domain.calendar.dto.RequestCalendarDetail;
import com.elice.meetstudy.domain.calendar.dto.ResponseCalendarDetail;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-05T01:07:36+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class CalendarDetailMapperImpl implements CalendarDetailMapper {

    @Override
    public ResponseCalendarDetail toResponseCalendarDetail(Calendar_detail calendarDetail) {
        if ( calendarDetail == null ) {
            return null;
        }

        boolean isHoliday = false;
        long id = 0L;
        String title = null;
        String content = null;
        String startDay = null;
        String endDay = null;
        String startTime = null;
        String endTime = null;

        isHoliday = calendarDetail.isHoliday();
        if ( calendarDetail.getId() != null ) {
            id = calendarDetail.getId();
        }
        title = calendarDetail.getTitle();
        content = calendarDetail.getContent();
        startDay = calendarDetail.getStartDay();
        endDay = calendarDetail.getEndDay();
        startTime = calendarDetail.getStartTime();
        endTime = calendarDetail.getEndTime();

        ResponseCalendarDetail responseCalendarDetail = new ResponseCalendarDetail( id, title, content, startDay, endDay, startTime, endTime, isHoliday );

        return responseCalendarDetail;
    }

    @Override
    public Calendar_detail toCalendarDetail(RequestCalendarDetail requestCalendarDetail) {
        if ( requestCalendarDetail == null ) {
            return null;
        }

        Calendar_detail.Calendar_detailBuilder calendar_detail = Calendar_detail.builder();

        calendar_detail.title( requestCalendarDetail.title() );
        calendar_detail.content( requestCalendarDetail.content() );
        calendar_detail.startDay( requestCalendarDetail.startDay() );
        calendar_detail.endDay( requestCalendarDetail.endDay() );
        calendar_detail.startTime( requestCalendarDetail.startTime() );
        calendar_detail.endTime( requestCalendarDetail.endTime() );

        return calendar_detail.build();
    }
}
