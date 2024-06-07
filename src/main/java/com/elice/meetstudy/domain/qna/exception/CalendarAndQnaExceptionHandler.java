package com.elice.meetstudy.domain.qna.exception;

import com.elice.meetstudy.domain.user.jwt.web.json.ApiResponseJson;
import com.elice.meetstudy.domain.user.jwt.web.json.ResponseStatusCode;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class CalendarAndQnaExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PasswordValidationException.class)
    public ApiResponseJson handleCustomValidationException(PasswordValidationException ex, WebRequest request) {
        return  new ApiResponseJson(HttpStatus.BAD_REQUEST, ResponseStatusCode.WRONG_PARAMETER,
            Map.of("errMsg", "비밀글에는 비밀번호가 필요합니다."));
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(SingleResponseViolationException.class)
    public ApiResponseJson SingleResponseViolationException(SingleResponseViolationException ex,
       WebRequest request){
        return new ApiResponseJson(HttpStatus.CONFLICT,
            Map.of("errMsg", "질문 하나에는 하나의 답변만 등록할 수 있습니다."));
    }

}
