package com.bookease.bookease.handlers;
import com.bookease.bookease.exceptions.EventFullException;
import com.bookease.bookease.exceptions.ForbiddenException;
import com.bookease.bookease.exceptions.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.security.SignatureException;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    public ProblemDetail handleSecutiryException;


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityExceptions(Exception ex) {
        ProblemDetail errorDetail = null;
        if(ex instanceof BadCredentialsException){
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.valueOf(401), ex.getMessage());
            errorDetail.setProperty("acess_denied_reason", "Authentication Failure");
        }

        if(ex instanceof AccessDeniedException){
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.valueOf(403), ex.getMessage());
            errorDetail.setProperty("acess_denied_reason", "not_authorized");
        }

        if(ex instanceof SignatureException){
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.valueOf(403), ex.getMessage());
            errorDetail.setProperty("acess_denied_reason", "JWT Signature not valid");
        }

        return errorDetail;
    }

    @ExceptionHandler(EventFullException.class)
    public ProblemDetail handleEventFullException(EventFullException ex) {
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.valueOf(400), ex.getMessage());
        errorDetail.setProperty("acess_denied_reason", "Event is full");
        errorDetail.setProperty("timestamp", LocalDateTime.now()); // Optional metadata
        return errorDetail;

    }

    @ExceptionHandler(ForbiddenException.class)
    public ProblemDetail handleForbiddenException(EventFullException ex) {
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, ex.getMessage());
        errorDetail.setProperty("acess_denied_reason", "An unexpected error occurred");
        errorDetail.setProperty("timestamp", LocalDateTime.now()); // Optional metadata
        return errorDetail;

    }

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleNotFoundException(EventFullException ex) {
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        errorDetail.setProperty("acess_denied_reason", "An unexpected error occurred");
        errorDetail.setProperty("timestamp", LocalDateTime.now()); // Optional metadata
        return errorDetail;

    }
}
