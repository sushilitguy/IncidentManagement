package com.demo.incidentmanagement.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to manage centralized exceptions
 */
@RestControllerAdvice
public class ExceptionManager {

    /**
     * Handles IncidentException
     * @param ex IncidentException object
     * @return Response in case of error
     */
    @ExceptionHandler(IncidentException.class)
    public ResponseEntity<Map<String, Object>> handleIncidentException(IncidentException ex) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", LocalDateTime.now());
        errorBody.put("message", ex.getMessage());
        errorBody.put("status", ex.getHttpStatus());
        errorBody.put("error", ex.getHttpStatus().toString());

        return new ResponseEntity<>(errorBody, ex.getHttpStatus());
    }
}
