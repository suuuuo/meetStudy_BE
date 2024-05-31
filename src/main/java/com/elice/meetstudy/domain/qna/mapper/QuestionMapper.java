package com.elice.meetstudy.domain.qna.mapper;

import com.elice.meetstudy.domain.calendar.dto.RequestCalendarDetail;
import com.elice.meetstudy.domain.qna.domain.Question;
import com.elice.meetstudy.domain.qna.dto.RequestQuestionDto;
import com.elice.meetstudy.domain.qna.dto.ResponseQuestionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuestionMapper {

    @Mapping(source = "secret", target = "isSecret")
    ResponseQuestionDto toResponseQuestionDto(Question question);

    Question toQuestionEntity (RequestQuestionDto requestQuestionDto);
}
