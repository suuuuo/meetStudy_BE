package com.elice.meetstudy.domain.qna.exception;

public class PasswordValidationException extends RuntimeException{

    public PasswordValidationException(String message) {
        super(message);
    }
}
