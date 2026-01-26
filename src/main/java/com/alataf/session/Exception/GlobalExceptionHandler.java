package com.alataf.session.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.alataf.session.DTO.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConcurrentLoginException.class)
    public ResponseEntity<ErrorResponse> handleConcurrentLogin(
            ConcurrentLoginException ex) {

        ErrorResponse error = new ErrorResponse(
                "SESSION_CONCURRENT_LOGIN",
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT) // 409
                .body(error);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleGeneric(RuntimeException ex) {

        ErrorResponse error = new ErrorResponse(
                "INTERNAL_ERROR",
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }
}

