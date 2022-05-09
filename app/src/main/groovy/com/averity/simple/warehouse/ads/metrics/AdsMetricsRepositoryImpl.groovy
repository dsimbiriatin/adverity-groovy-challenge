package com.averity.simple.warehouse.ads.metrics

import com.averity.simple.warehouse.ads.metrics.constants.SupportedMetrics
import com.averity.simple.warehouse.ads.metrics.query.AdsMetrics
import com.averity.simple.warehouse.api.model.AdsMetricsRequest
import org.jooq.DSLContext
import org.jooq.Field
import org.jooq.Table
import org.springframework.lang.NonNull
import org.springframework.stereotype.Repository

import static com.averity.simple.warehouse.jooq.tables.CampaignDim.CAMPAIGN_DIM
import static com.averity.simple.warehouse.jooq.tables.DatasourceDim.DATASOURCE_DIM
import static com.averity.simple.warehouse.jooq.tables.DateDim.DATE_DIM
import static com.averity.simple.warehouse.jooq.tables.Metrics.METRICS
import static org.jooq.impl.DSL.round
import static org.jooq.impl.DSL.sum

@Repository
class AdsMetricsRepositoryImpl implements AdsMetricsRepository {

    private static final Set<Table> DIMENSIONS = [DATASOURCE_DIM, CAMPAIGN_DIM]

    private final DSLContext dsl

    AdsMetricsRepositoryImpl(DSLContext dsl) {
        this.dsl = dsl
    }

    @NonNull
    @Override
    List<AdsMetrics> queryMetrics(@NonNull AdsMetricsRequest request) {
        // select
        def metricFields = request.metrics.collect { metricField(it) }
        def groupByFields = request.groupBy.collect { dimensionField(it) }
        def select = dsl.select(metricFields + groupByFields).from(METRICS)

        // join
        def uniqueDimensions = (request.filters.keySet() + request.groupBy) as Set
        def dimensionsToJoin = uniqueDimensions.collect { dimensionsTable(it) }
        if (request.dateRange) {
            dimensionsToJoin << DATE_DIM
        }
        if (dimensionsToJoin) {
            dimensionsToJoin.each { select.join(it).onKey() }
        }

        // where
        def conditions = request.filters.collect { dimensionField(it.key).eq(it.value) }
        if (request.dateRange) {
            conditions << DATE_DIM.DATE.between(
                    request.dateRange.from.toLocalDate(),
                    request.dateRange.to.toLocalDate()
            )
        }
        if (conditions) {
            select.where(conditions)
        }

        // group by
        if (groupByFields) {
            select.groupBy((Collection<Field>) groupByFields)
        }
        select.fetch({ record ->
            new AdsMetrics(
                    record.field(METRICS.CLICKS)?.get(record) as BigInteger,
                    record.field(METRICS.IMPRESSIONS)?.get(record) as BigInteger,
                    record.field(SupportedMetrics.CTR)?.get(record) as Double,
                    record.field(DATASOURCE_DIM.DATASOURCE)?.get(record),
                    record.field(CAMPAIGN_DIM.CAMPAIGN)?.get(record)
            )
        })
    }

    private Field metricField(String name) {
        if (SupportedMetrics.CTR == name) {
            return ctrField()
        }
        def field = METRICS.field(name)
        sum(field).as(name)
    }

    private Field ctrField() {
        def sumClicks = sum(METRICS.CLICKS)
        def sumImpressions = sum(METRICS.IMPRESSIONS)
        round(sumClicks.divide(sumImpressions).multiply(100), 2).as(SupportedMetrics.CTR)
    }

    private Field dimensionField(String name) {
        DIMENSIONS.findResult { it.field(name) }
    }

    private Table dimensionsTable(String name) {
        DIMENSIONS.find { it.field(name) }
    }
}
