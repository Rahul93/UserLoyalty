package com.acko.template.validators;

import com.acko.template.pojo.ValidationResult;

@FunctionalInterface
public interface Validation<K> {

  ValidationResult test(K k);
}
