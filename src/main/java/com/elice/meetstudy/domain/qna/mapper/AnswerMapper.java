package com.elice.meetstudy.domain.qna.mapper;

import com.elice.meetstudy.domain.qna.domain.Answer;
import com.elice.meetstudy.domain.qna.dto.RequestAnswerDto;
import com.elice.meetstudy.domain.qna.dto.ResponseAnswerDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnswerMapper {
    Answer toAnswerEntity(RequestAnswerDto requestAnswerDto);

    ResponseAnswerDto toResponseAnswerDto(Answer answer);
}
