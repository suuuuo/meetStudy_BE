package com.elice.meetstudy.domain.qna.exception;

public class SingleResponseViolationException extends RuntimeException {

    public SingleResponseViolationException(String message){
        super(message);
    }
}
