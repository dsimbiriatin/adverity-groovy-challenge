package com.averity.simple.warehouse.api.validations

import com.averity.simple.warehouse.ads.metrics.constants.SupportedMetrics

import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class SupportedMetricsOnlyValidator implements ConstraintValidator<SupportedMetricsOnly, Collection<String>> {

    @Override
    boolean isValid(Collection<String> value, ConstraintValidatorContext context) {
        !value || SupportedMetrics.ALL.containsAll(value)
    }
}
