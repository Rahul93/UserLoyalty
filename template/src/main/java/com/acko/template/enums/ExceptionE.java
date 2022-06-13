package com.acko.template.enums;

import com.acko.template.interfaceI.IException;
import com.acko.template.utils.Util;
import lombok.Getter;
import com.google.common.base.Enums;

@Getter
public enum ExceptionE implements IException {

  BAD_REQUEST_EXCEPTION(ErrorCode.BAD_REQUEST_ERROR),
  INTERNAL_SERVER_ERROR(ErrorCode.UNCATEGORIZED_ERROR);

  private ErrorCode errorCode;

  ExceptionE(ErrorCode errorCode) {
    this.errorCode = errorCode;
  }

  public static ExceptionE getExceptionE(final Object e) {
    return Enums.getIfPresent(
            ExceptionE.class, Util.camelToUpperSnakeCase(e.getClass().getSimpleName()))
        .or(ExceptionE.INTERNAL_SERVER_ERROR);
  }
}
