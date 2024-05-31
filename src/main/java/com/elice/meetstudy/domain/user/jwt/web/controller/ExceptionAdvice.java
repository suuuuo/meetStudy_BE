package com.elice.meetstudy.domain.user.jwt.web.controller;

import com.elice.meetstudy.domain.user.jwt.web.json.ApiResponseJson;
import com.elice.meetstudy.domain.user.jwt.web.json.ResponseStatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
public class ExceptionAdvice {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ApiResponseJson handleRunTimeException(RuntimeException e){
        log.error("", e);
        return new ApiResponseJson(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusCode.SERVER_ERROR,
                Map.of("errMsg", "서버에 오류가 발생했습니다."));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalStateException.class, IllegalArgumentException.class})
    public ApiResponseJson handleBAD_REQUESTException(Exception e){
        log.error("", e);
        return new ApiResponseJson(HttpStatus.BAD_REQUEST, ResponseStatusCode.WRONG_PARAMETER,
                Map.of("errMsg", e.getMessage()));
    }
}
