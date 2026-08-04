package com.example.distributed_search_engine.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handle(IllegalArgumentException ex) {

        return Map.of(
                "timestamp", Instant.now(),
                "message", ex.getMessage()
        );

    }

}