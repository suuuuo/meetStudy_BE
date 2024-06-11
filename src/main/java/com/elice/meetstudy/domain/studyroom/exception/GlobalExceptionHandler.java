package com.elice.meetstudy.domain.studyroom.exception;

import com.elice.meetstudy.domain.studyroom.DTO.ErrorBodyDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorBodyDTO(404L, ex.getMessage()));
    }

    @ExceptionHandler(CustomNotValidException.class)
    public ResponseEntity<?> dataValidationException(CustomNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorBodyDTO(400L, ex.getMessage()));
    }

    @ExceptionHandler(StudyRoomAuthenticationException.class)
    public ResponseEntity<?> ownerAuthenticationException(StudyRoomAuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorBodyDTO(403L, ex.getMessage()));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> dataValidationException(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        Map<String, Object> errors = new HashMap<>();
        if (fieldErrors.size() > 1) {
            Map<String, List<String>> fieldErrorMessages = fieldErrors.stream()
                    .collect(Collectors.groupingBy(FieldError::getField,
                            Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())));
            errors.put("errors", fieldErrorMessages);
        } else {
            FieldError fieldError = fieldErrors.get(0);
            errors.put("error", fieldError.getDefaultMessage());
        }

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
