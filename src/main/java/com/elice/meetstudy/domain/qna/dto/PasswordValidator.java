package com.elice.meetstudy.domain.qna.dto;


import com.elice.meetstudy.domain.qna.exception.PasswordValidationException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ValidationException;
import org.apache.coyote.BadRequestException;
import org.springframework.security.web.firewall.RequestRejectedException;

public class PasswordValidator implements ConstraintValidator<PasswordCheck, RequestQuestionDto> {

    @Override
    public void initialize(PasswordCheck constraintAnnotation) {
    }

    @Override
    public boolean isValid(RequestQuestionDto dto, ConstraintValidatorContext context) {
        System.out.println(dto.isSecret());
    if (dto.isSecret() && (dto.password() == null || dto.password().isBlank())) {
        throw new PasswordValidationException("비밀번호를 입력해주세요.");
    }
        return true;
    }
}