package com.example.springboot_module4.demo.advices;


import com.example.springboot_module4.demo.exceptions.ResourceNotFoundException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException exception) {
        ApiError apiError = new ApiError(exception.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthenticationException(AuthenticationException exception) {
        ApiError apiError = new ApiError(exception.getMessage(), HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JwtException.class) public ResponseEntity<ApiError> handleJwtException(JwtException exception) {
        ApiError apiError = new ApiError(exception.getMessage(), HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }
}
