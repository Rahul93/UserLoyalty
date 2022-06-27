package com.acko.template.validators;

import org.apache.commons.lang.StringUtils;
import java.util.Objects;

public class ValidationUtil {

  public static Validation<Object> notNull(String field) {
    return GenericValidation.from(Objects::nonNull, field + " should not be null");
  }

  public static Validation<String> notEmpty(String field) {
    return GenericValidation.from(s -> !StringUtils.isEmpty(s), field + " should not be null or empty");
  }

  public static Validation<String> isValidDouble(String field) {
    return GenericValidation.from(
        s -> {
          try {
            Double.parseDouble(s);
            return true;
          } catch (NumberFormatException ex) {
            return false;
          }
        },
        field + " should be a valid double number");
  }
}
