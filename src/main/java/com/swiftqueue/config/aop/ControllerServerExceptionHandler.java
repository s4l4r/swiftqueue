package com.swiftqueue.config.aop;

import com.swiftqueue.dto.ErrorDTO;
import com.swiftqueue.exception.server.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RestControllerAdvice
public class ControllerServerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
        ErrorDTO errorDTO = ErrorDTO.builder().errorType("VALIDATION")
        .errors(errors).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorDTO errorDTO = ErrorDTO.builder().errorType("NOT_FOUND")
                .errors(Collections.singletonList(ex.getMessage())).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
    }
}
