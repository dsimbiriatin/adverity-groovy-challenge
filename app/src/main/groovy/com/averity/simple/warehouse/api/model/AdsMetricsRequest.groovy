package com.averity.simple.warehouse.api.model

import com.averity.simple.warehouse.api.validations.SupportedDimensionsOnly
import com.averity.simple.warehouse.api.validations.SupportedMetricsOnly
import groovy.transform.Immutable

import javax.validation.Valid
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import java.time.OffsetDateTime

@Immutable
class AdsMetricsRequest {

    @NotEmpty
    @SupportedMetricsOnly
    Set<String> metrics = []

    @SupportedDimensionsOnly
    Set<String> groupBy = []

    @SupportedDimensionsOnly
    Map<String, String> filters = [:]

    @Valid
    DateRange dateRange

    @Immutable
    static class DateRange {

        @NotNull
        OffsetDateTime from

        @NotNull
        OffsetDateTime to
    }
}
