package com.elice.meetstudy.domain.calendar.mapper;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class MappingUtils {

    @Named("nullToEmpty")
    public String nullToEmpty(String value) {
        return value == null ? "" : value;
    }
}
