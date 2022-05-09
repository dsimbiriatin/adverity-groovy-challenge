package com.averity.simple.warehouse.ads.metrics

import com.averity.simple.warehouse.ads.metrics.constants.SupportedMetrics
import com.averity.simple.warehouse.ads.metrics.query.AdsMetricsQueryResult
import com.averity.simple.warehouse.api.model.AdsMetricsRequest
import org.springframework.lang.NonNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdsMetricsServiceImpl implements AdsMetricsService {

    private final AdsMetricsRepository repository

    AdsMetricsServiceImpl(AdsMetricsRepository repository) {
        this.repository = repository
    }

    @NonNull
    @Override
    @Transactional(readOnly = true)
    AdsMetricsQueryResult queryAndAccumulate(@NonNull AdsMetricsRequest request) {
        def accumulator = AdsMetricsQueryResult.accumulator()
        repository.queryMetrics(request).each { accumulator.nextEntry(it) }
        accumulator.toQueryResult()
            .calculateCtr(SupportedMetrics.CTR in request.metrics)
            .includeEntries(!request.groupBy.empty)
            .get()
    }
}
