package com.averity.simple.warehouse.api.model

import com.averity.simple.warehouse.ads.metrics.query.AdsMetrics
import com.averity.simple.warehouse.ads.metrics.query.AdsMetricsQueryResult
import groovy.transform.Immutable
import org.springframework.lang.NonNull

@Immutable
class AdsMetricsResponse {

    Totals totals
    List<AdsMetrics> entries

    @Immutable
    static class Totals {
        BigInteger clicks
        BigInteger impressions
        Double ctr
    }

    static AdsMetricsResponse of(@NonNull AdsMetricsQueryResult result) {
        def totals = new Totals(result.totalClicks, result.totalImpressions, result.totalCtr)
        new AdsMetricsResponse(totals, result.selectedEntries)
    }
}
