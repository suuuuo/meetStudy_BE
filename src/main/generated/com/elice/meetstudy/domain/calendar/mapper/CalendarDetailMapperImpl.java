package com.elice.meetstudy.domain.calendar.mapper;

import com.elice.meetstudy.domain.calendar.domain.Calendar_detail;
import com.elice.meetstudy.domain.calendar.dto.RequestCalendarDetail;
import com.elice.meetstudy.domain.calendar.dto.ResponseCalendarDetail;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-07T15:57:20+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 19.0.2 (Oracle Corporation)"
)
@Component
public class CalendarDetailMapperImpl implements CalendarDetailMapper {

    @Autowired
    private MappingUtils mappingUtils;

    @Override
    public ResponseCalendarDetail toResponseCalendarDetail(Calendar_detail calendarDetail) {
        if ( calendarDetail == null ) {
            return null;
        }

        boolean isHoliday = false;
        String title = null;
        String content = null;
        String startDay = null;
        String endDay = null;
        String startTime = null;
        String endTime = null;
        long id = 0L;

        isHoliday = calendarDetail.isHoliday();
        title = mappingUtils.nullToEmpty( calendarDetail.getTitle() );
        content = mappingUtils.nullToEmpty( calendarDetail.getContent() );
        startDay = mappingUtils.nullToEmpty( calendarDetail.getStartDay() );
        endDay = mappingUtils.nullToEmpty( calendarDetail.getEndDay() );
        startTime = mappingUtils.nullToEmpty( calendarDetail.getStartTime() );
        endTime = mappingUtils.nullToEmpty( calendarDetail.getEndTime() );
        if ( calendarDetail.getId() != null ) {
            id = calendarDetail.getId();
        }

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
