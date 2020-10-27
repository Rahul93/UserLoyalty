package com.acko.template.constant;

import com.acko.template.response.error.ApiError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalCustomExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handle MissingServletRequestParameterException. Triggered when a 'required' request parameter
     * is missing.
     *
     * @param ex      MissingServletRequestParameterException
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
        MissingServletRequestParameterException ex, HttpHeaders headers,
        HttpStatus status, WebRequest request
    ) {
        String error = ex.getParameterName() + " parameter is missing";
        logger.error(error);
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
    }

    /**
     * Handle HttpMediaTypeNotSupportedException. This one triggers when JSON is invalid as well.
     *
     * @param ex      HttpMediaTypeNotSupportedException
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
        HttpMediaTypeNotSupportedException ex,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
        return buildResponseEntity(new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE,
            builder.substring(0, builder.length() - 2), ex));
    }

    /**
     * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid validation.
     *
     * @param ex      the MethodArgumentNotValidException that is thrown when @Valid validation
     *                fails
     * @param headers
     * @param status
     * @param request
     * @return
     */

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.getError().setMessage("Validation error");
        apiError.addValidationErrors(ex.getBindingResult().getFieldErrors());
        apiError.addValidationError(ex.getBindingResult().getGlobalErrors());
        return buildResponseEntity(apiError);
    }

    /**
     * @param ex      HttpMessageNotReadableException, Happens when request JSON is malformed
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
        HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status,
        WebRequest request) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
//        log.info("{} to {}", servletWebRequest.getHttpMethod(),
//            servletWebRequest.getRequest().getServletPath());
        String error = "Malformed JSON request";
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError error) {
        return new ResponseEntity<>(error, error.getStatus());
    }
}
