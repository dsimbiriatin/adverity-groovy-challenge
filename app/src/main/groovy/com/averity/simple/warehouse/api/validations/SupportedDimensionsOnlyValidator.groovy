package com.averity.simple.warehouse.api.validations

import com.averity.simple.warehouse.ads.metrics.constants.SupportedDimensions

import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class SupportedDimensionsOnlyValidator implements ConstraintValidator<SupportedDimensionsOnly, Object> {

    @Override
    boolean isValid(Object value, ConstraintValidatorContext context) {
        switch (value) {
            case { it instanceof Map } -> SupportedDimensions.ALL.containsAll(value.keySet())
            case { it instanceof Collection } -> SupportedDimensions.ALL.containsAll(value)
            default -> true
        }
    }
}
