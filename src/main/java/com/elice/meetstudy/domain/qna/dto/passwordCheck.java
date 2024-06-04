package com.elice.meetstudy.domain.qna.dto;

import jakarta.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//비밀글 설정일 때 비밀번호가 입력되어 있는지 확인
@Target({ElementType.TYPE})
@Constraint(validatedBy = PasswordValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface passwordCheck {

    String message() default "비밀글에는 비밀번호가 필수적으로 필요합니다! (4자 이상)";

    Class[] groups() default {};

    Class[] payload() default {};
}
