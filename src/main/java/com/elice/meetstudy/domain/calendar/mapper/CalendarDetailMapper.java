package com.elice.meetstudy.domain.calendar.mapper;

import com.elice.meetstudy.domain.calendar.domain.Calendar_detail;
import com.elice.meetstudy.domain.calendar.dto.RequestCalendarDetail;
import com.elice.meetstudy.domain.calendar.dto.ResponseCalendarDetail;
import com.elice.meetstudy.domain.calendar.dto.ResponseAllCalendarDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = MappingUtils.class)
public interface CalendarDetailMapper {

    @Mapping(source = "holiday", target = "isHoliday")
    @Mapping(source = "title", target = "title", qualifiedByName = "nullToEmpty")
    @Mapping(source = "content", target = "content", qualifiedByName = "nullToEmpty")
    @Mapping(source = "startDay", target = "startDay", qualifiedByName = "nullToEmpty")
    @Mapping(source = "endDay", target = "endDay", qualifiedByName = "nullToEmpty")
    @Mapping(source = "startTime", target = "startTime", qualifiedByName = "nullToEmpty")
    @Mapping(source = "endTime", target = "endTime", qualifiedByName = "nullToEmpty")
    ResponseCalendarDetail toResponseCalendarDetail(Calendar_detail calendarDetail);

    Calendar_detail toCalendarDetail(RequestCalendarDetail requestCalendarDetail);
}
