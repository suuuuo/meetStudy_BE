package com.elice.meetstudy.domain.studyroom.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;


public @interface UserStudyRoomAnnotation {
    @Target(METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    @ApiResponse(content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = """
                    {
                        "id": 3,
                        "joinDate": "2024-06-04T10:10:14.366+00:00",
                        "permission": "MEMBER",
                        "studyRoomId": 2,
                        "user": {
                            "email": "mynew@email.test",
                            "password": "$bb$bb$bb/bb/abcdefghijklmnopqrstuvwxyz0123456789./ABCDEFGHI"
                        }
                    }
                    """)))
    @interface Success {
        String responseCode() default "200";
        String description();
    }
}
