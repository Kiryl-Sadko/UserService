package com.hes.test.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<CustomExceptionResponse> handleRuntimeException(RuntimeException exception) {
        CustomExceptionResponse exceptionResponse = new CustomExceptionResponse();
        exceptionResponse.setErrorStatus(BAD_REQUEST);
        exceptionResponse.setErrorMessage(exception.getMessage());
        exceptionResponse.setErrorCode(String.valueOf(exceptionResponse.getErrorStatus().value()).concat("-01"));
        return new ResponseEntity<>(exceptionResponse, BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<CustomExceptionResponse> handleNoSuchElementException(NoSuchElementException exception) {
        CustomExceptionResponse exceptionResponse = new CustomExceptionResponse();
        exceptionResponse.setErrorStatus(NOT_FOUND);
        exceptionResponse.setErrorMessage(exception.getMessage());
        exceptionResponse.setErrorCode(String.valueOf(exceptionResponse.getErrorStatus().value()).concat("-02"));
        return new ResponseEntity<>(exceptionResponse, NOT_FOUND);
    }

    @ExceptionHandler(InvalidDtoException.class)
    protected ResponseEntity<CustomExceptionResponse> handleInvalidDtoException(InvalidDtoException exception) {
        CustomExceptionResponse exceptionResponse = new CustomExceptionResponse();
        exceptionResponse.setErrorStatus(BAD_REQUEST);
        exceptionResponse.setErrorMessage(exception.getMessage());
        exceptionResponse.setErrorCode(String.valueOf(exceptionResponse.getErrorStatus().value()).concat("-03"));
        return new ResponseEntity<>(exceptionResponse, BAD_REQUEST);
    }
}
