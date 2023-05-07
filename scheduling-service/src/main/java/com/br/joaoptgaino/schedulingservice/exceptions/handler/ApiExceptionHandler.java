package com.br.joaoptgaino.schedulingservice.exceptions.handler;

import com.br.joaoptgaino.schedulingservice.exceptions.BusinessException;
import com.br.joaoptgaino.schedulingservice.exceptions.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getDefaultMessage() != null ? fieldError.getDefaultMessage() : "").toList();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(ex.getStatusCode().toString())
                .message(ex.getMessage())
                .errors(errors)
                .build();
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(errorResponse);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(ex.getMessage())
                .message(ex.getMessage())
                .errors(ex.getErrors())
                .build();
        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
    }
}
