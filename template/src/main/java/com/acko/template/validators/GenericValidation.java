package com.acko.template.validators;

import com.acko.template.pojo.ValidationResult;
import java.util.function.Predicate;

public class GenericValidation<K> implements Validation<K> {

  private final Predicate<K> predicate;
  private final String errorMessage;

  private GenericValidation(Predicate<K> predicate, String errorMessage) {
    this.predicate = predicate;
    this.errorMessage = errorMessage;
  }

  public static <K> GenericValidation<K> from(Predicate<K> predicate, String errorMessage) {
    return new GenericValidation<>(predicate, errorMessage);
  }

  @Override
  public ValidationResult test(K k) {
    return predicate.test(k) ? ValidationResult.ok() : ValidationResult.fail(this.errorMessage);
  }
}
