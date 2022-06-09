package com.acko.template.pojo;

import com.acko.template.exceptions.BadRequestException;
import lombok.Data;
import java.util.Optional;

@Data
public class ValidationResult {

  private boolean isValid;
  private Optional<String> message;

  public ValidationResult(Boolean isValid) {
    this.isValid = isValid;
  }

  public ValidationResult(Boolean isValid, String message) {
    this.isValid = isValid;
    this.message = Optional.ofNullable(message);
  }

  public static ValidationResult ok() {
    return new ValidationResult(true);
  }

  public static ValidationResult fail(String errorMessage) {
    return new ValidationResult(false, errorMessage);
  }

  public void throwIfFail() {

    if (!this.isValid) {
      throw new BadRequestException(this.message.orElse("Validation exception"));
    }
  }
}
