package com.david.demo.errorhandling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class RestExceptionHandling extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { RuntimeException.class })
    protected ResponseEntity<Object> handleUnexpectedError(RuntimeException ex, WebRequest request) {

        log.error("An unexpected error occured.", ex);

        return handleExceptionInternal(ex, new ErrorResponse("I hate to say this, but an unexpected error occurred."),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = { LoadDataException.class })
    protected ResponseEntity<Object> handleLoadData(LoadDataException ex, WebRequest request) {

        return handleExceptionInternal(ex, new ErrorResponse(ex.getMessage()),
                new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE, request);
    }
}
