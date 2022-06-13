package com.acko.template.enums;

import com.acko.template.response.error.ApiError;
import com.acko.template.response.error.ErrorObject;
import com.acko.template.threadlocal.ThreadContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

@Getter
@AllArgsConstructor
public enum ErrorCode {

  BAD_REQUEST_ERROR(ErrorGroup.BAD_REQUEST,"Bad Request","001"),
  UNCATEGORIZED_ERROR(ErrorGroup.GENERAL_ERRORS, "Internal Server Error", "001");

  private ErrorGroup group;
  private String message;
  private String code;

  private static ErrorObject getErrorObject(final ErrorCode errorCode) {
    return new ErrorObject(
        errorCode.getGroup().getPrefix() + errorCode.getCode(), errorCode.getMessage());
  }

  public static ResponseEntity<Object> getErrorResponse(
      final ErrorCode errorCode, final String errorMessage, boolean useErrorMessage) {

    ErrorObject errorObject = ErrorCode.getErrorObject(errorCode);

    /* If we want to print customise error message, we can set the message here
    otherwise it will print generic exception message */
    if (useErrorMessage) {
      errorObject.setMessage(errorMessage);
    }

    ApiError error =
        ApiError.builder()
            .success(false)
            .traceId(
                (ThreadContext.getRequestContext() == null
                        || StringUtils.isEmpty(
                            ThreadContext.getRequestContext().get().getTraceId()))
                    ? null
                    : ThreadContext.getRequestContext().get().getTraceId())
            .error(errorObject)
            .build();

    return new ResponseEntity<>(error, errorCode.getGroup().getHttpStatus());
  }
}
