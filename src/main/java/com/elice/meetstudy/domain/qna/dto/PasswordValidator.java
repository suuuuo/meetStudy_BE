package com.elice.meetstudy.domain.qna.dto;

import com.elice.meetstudy.domain.qna.exception.PasswordValidationException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class PasswordValidator implements ConstraintValidator<PasswordCheck, RequestQuestionDto> {

    @Override
    public void initialize(PasswordCheck constraintAnnotation) {
    }

    @Override
    public boolean isValid(RequestQuestionDto dto, ConstraintValidatorContext context) {
    if (dto.isSecret() && (dto.password() == null || dto.password().isBlank() || dto.password().isEmpty())) {
        throw new PasswordValidationException("비밀번호를 입력해주세요.");
    }
        return true;
    }
}