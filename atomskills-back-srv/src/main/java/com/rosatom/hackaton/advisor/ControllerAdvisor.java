package com.rosatom.hackaton.advisor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class, IllegalArgumentException.class})
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            Exception ex,
            WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("Error", ex.getMessage());
        body.put("Time", LocalDateTime.now());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

}
