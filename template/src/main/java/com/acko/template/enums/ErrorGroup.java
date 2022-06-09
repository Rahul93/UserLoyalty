package com.acko.template.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorGroup {
  BAD_REQUEST("BADERR_", HttpStatus.BAD_REQUEST),
  GENERAL_ERRORS("GENERR_", HttpStatus.INTERNAL_SERVER_ERROR);

  ErrorGroup(String prefix, HttpStatus httpStatus) {
    this.prefix = prefix;
    this.httpStatus = httpStatus;
  }

  private String prefix;
  private HttpStatus httpStatus;
}
