package com.averity.simple.warehouse.api.validations

import javax.validation.Constraint
import javax.validation.Payload
import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SupportedDimensionsOnlyValidator)
@interface SupportedDimensionsOnly {

    String message() default 'Unsupported dimension'

    Class<?>[] groups() default []

    Class<? extends Payload>[] payload() default []
}