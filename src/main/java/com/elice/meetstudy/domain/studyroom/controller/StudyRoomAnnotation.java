package com.elice.meetstudy.domain.studyroom.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

public @interface StudyRoomAnnotation {
    @Target(METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    @ApiResponse(content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = """
                    {
                        "id": 1,
                        "title": "스프링부트 스터디룸",
                        "description": "스프링부트를 공부하는 스터디룸입니다.",
                        "createdDate": "2024-06-04T08:04:12.294+00:00",
                        "maxCapacity": "32",
                        "userStudyRooms": [
                                {
                                    "id": 5,
                                    "joinDate": "2024-06-04T08:04:12.433+00:00",
                                    "permission": "OWNER",
                                    "studyRoomId": 1,
                                    "user": {
                                        "email": "test@email.com",
                                        "password": "$aa$aa$aa/aa/abcdefghijklmnopqrstuvwxyz0123456789./ABCDEFGHI"
                                    }
                                }
                        ]
                    }
                    """)))
    @interface Success {
        String responseCode() default "200";
        String description();
    }

    @Target(METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    @ApiResponse(content = @Content(mediaType = "text/html"))
    @interface Failure {
        String responseCode() default "404";
        String description() default "해당 스터디 룸을 찾을 수 없음";

    }
}



