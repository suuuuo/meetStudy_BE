package com.elice.meetstudy.domain.studyroom.exception;

import org.springframework.security.core.AuthenticationException;

public class StudyRoomAuthenticationException extends AuthenticationException {
    public StudyRoomAuthenticationException(String msg) {
        super(msg);
    }
}
