package com.elice.meetstudy.domain.calendar.mapper;

import com.elice.meetstudy.domain.calendar.domain.Calendar_detail;
import com.elice.meetstudy.domain.calendar.dto.RequestCalendarDetail;
import com.elice.meetstudy.domain.calendar.dto.ResponseCalendarDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CalendarDetailMapper {
    @Mapping(source = "holiday", target = "isHoliday")
    ResponseCalendarDetail toResponseCalendarDetail(Calendar_detail calendarDetail);

    Calendar_detail toCalendarDetail(RequestCalendarDetail requestCalendarDetail);

    //테스트용
    RequestCalendarDetail toRequestCalendarDetail(Calendar_detail calendarDetail);
}
