package com.fernando84.employeeapi.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fernando84.employeeapi.DTO.ErrorResponse;
import com.fernando84.employeeapi.exception.DepartmentNotFoundException;
import com.fernando84.employeeapi.exception.EmployeeNotFoundException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // --- Business exceptions: "Not Found" ---
    @ExceptionHandler({ EmployeeNotFoundException.class, DepartmentNotFoundException.class })
    public ResponseEntity<ErrorResponse> handleNotFound(RuntimeException ex, HttpServletRequest request) {
        log.error("Unhandled NotFound error on {}: ", request.getRequestURI(), ex);
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    // --- @RequestBody validations with @Valid (Bean Validation) ---
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .collect(Collectors.joining(", "));
        log.error("Unhandled validation error on {}: ", request.getRequestURI(), ex);
        return buildResponse(HttpStatus.BAD_REQUEST, message, request);
    }

    // --- Security: invalid credentials on /auth/login ---
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthentication(AuthenticationException ex, HttpServletRequest request) {
        log.error("Unhandled authentication error on {}: ", request.getRequestURI(), ex);
        return buildResponse(HttpStatus.UNAUTHORIZED, "Invalid Credentials", request);
    }

    // --- Security: Authenticated User, not autoirzed ---
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex, HttpServletRequest request) {
        log.error("Unhandled accessDenied error on {}: ", request.getRequestURI(), ex);
        return buildResponse(HttpStatus.FORBIDDEN, "You're not autorized to complete this action.", request);
    }

    // --- Catch-all: unexpected exceptions ---
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex, HttpServletRequest request) {
        log.error("Unhandled error on {}: ", request.getRequestURI(), ex);
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", request);
    }

    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String message, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }
}
