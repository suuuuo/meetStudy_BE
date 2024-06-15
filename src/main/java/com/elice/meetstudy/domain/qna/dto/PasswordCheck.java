package com.elice.meetstudy.domain.qna.dto;

import jakarta.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface PasswordCheck {
    String message() default "비밀번호를 입력해주세요.";
    Class [] groups() default {};
    Class [] payload() default {};
}
