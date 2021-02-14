package com.swiftqueue.config.aop;

import com.swiftqueue.dto.ErrorDTO;
import com.swiftqueue.exception.business.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

@Component
@RestControllerAdvice
public class ControllerBusinessExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorDTO> handleLocationBusinessException(BusinessException ex) {
        ErrorDTO errorDTO = ErrorDTO.builder().errorType(ex.getType())
                .errors(Collections.singletonList(ex.getMessage())).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }
}
