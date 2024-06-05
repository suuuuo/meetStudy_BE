package com.elice.meetstudy.domain.qna.dto;


public record ResponseAnswerDto(
    long id,
    String content,
    String createdBy)
{}
