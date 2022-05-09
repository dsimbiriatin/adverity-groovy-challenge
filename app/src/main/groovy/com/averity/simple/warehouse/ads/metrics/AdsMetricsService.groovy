package com.averity.simple.warehouse.ads.metrics

import com.averity.simple.warehouse.ads.metrics.query.AdsMetricsQueryResult
import com.averity.simple.warehouse.api.model.AdsMetricsRequest
import org.springframework.lang.NonNull

interface AdsMetricsService {

    @NonNull
    AdsMetricsQueryResult queryAndAccumulate(@NonNull AdsMetricsRequest request)
}