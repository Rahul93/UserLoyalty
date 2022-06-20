package com.acko.template.controller;

import com.acko.template.enums.ErrorCode;
import com.acko.template.enums.ExceptionE;
import com.acko.template.exceptions.BadRequestException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import javax.servlet.http.HttpServletRequest;

@Log4j2
@ControllerAdvice
public class GlobalCustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        return buildResponse(ex, "Method argument not valid", false);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return buildResponse(ex, "Request Body is not valid", false);
    }

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<Object> handleBadRequestException(Exception ex, HttpServletRequest request) {
        return buildResponse(ex, "Request is not Valid", false);
    }


    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllException(Exception ex, HttpServletRequest request) {
        return buildResponse(ex, "Exception occurred",false);
    }

    private ResponseEntity<Object> buildResponse(final Exception ex, final String defaultMessage, boolean useCustomMessage) {
        ExceptionE exceptionE = ExceptionE.getExceptionE(ex);

        String message = exceptionE.getMessage(ex) != null ? exceptionE.getMessage(ex) : defaultMessage;
        log.error(message, ex.getMessage());

        return ErrorCode.getErrorResponse(exceptionE.getErrorCode(), message,useCustomMessage);
    }
}
