package com.averity.simple.warehouse.ads.metrics

import com.averity.simple.warehouse.ads.metrics.query.AdsMetrics
import com.averity.simple.warehouse.api.model.AdsMetricsRequest
import org.springframework.lang.NonNull

interface AdsMetricsRepository {

    @NonNull
    List<AdsMetrics> queryMetrics(@NonNull AdsMetricsRequest request)
}