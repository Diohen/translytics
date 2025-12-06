package com.diohen.translytics.handler;

import com.diohen.translytics.exception.UnprocessableTransactionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(UnprocessableTransactionException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public void handleUnprocessableException() {}

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleInvalidJson() {
        log.error("Received invalid JSON format.");
    }
}
