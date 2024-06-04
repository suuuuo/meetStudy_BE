package com.elice.meetstudy.domain.qna.dto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<passwordCheck, RequestQuestionDto> {

    @Override
    public boolean isValid(RequestQuestionDto requestQuestionDto,
        ConstraintValidatorContext context) {
        if(requestQuestionDto.isSecret() &&
            (requestQuestionDto.password() == null || requestQuestionDto.password().isEmpty())){
            return false;
        }
        return true;
    }
}