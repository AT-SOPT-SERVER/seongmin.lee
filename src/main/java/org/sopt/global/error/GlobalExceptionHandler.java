package org.sopt.global.error;

import org.sopt.global.error.exception.BusinessException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e){
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse errorResponse = ErrorResponse.of(errorCode);

        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorCode.getStatus()));
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleNotFoundException(NoResourceFoundException e){
        return new ResponseEntity<>(ErrorResponse.of(ErrorCode.NOT_FOUND_ERROR), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handlerHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
        return new ResponseEntity<>(ErrorResponse.of(ErrorCode.NOT_SUPPORTED_METHOD_ERROR), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleInternalServerError(RuntimeException e){
        return new ResponseEntity<>(ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
