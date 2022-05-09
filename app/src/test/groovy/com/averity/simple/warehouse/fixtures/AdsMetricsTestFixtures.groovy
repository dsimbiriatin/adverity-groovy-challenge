package com.averity.simple.warehouse.fixtures

import com.averity.simple.warehouse.ads.metrics.query.AdsMetrics
import com.averity.simple.warehouse.api.model.AdsMetricsRequest
import com.averity.simple.warehouse.api.model.AdsMetricsResponse

import java.time.OffsetDateTime

import static com.averity.simple.warehouse.ads.metrics.constants.SupportedDimensions.*
import static com.averity.simple.warehouse.ads.metrics.constants.SupportedMetrics.*
import static com.averity.simple.warehouse.ads.metrics.constants.SupportedMetrics.CLICKS

class AdsMetricsTestFixtures {

    // -- Requests
    static AdsMetricsRequest 'all metrics'() {
        new AdsMetricsRequest(metrics: [CLICKS, IMPRESSIONS, CTR])
    }

    static AdsMetricsRequest 'all clicks'() {
        new AdsMetricsRequest(metrics: [CLICKS])
    }

    static AdsMetricsRequest 'all impressions'() {
        new AdsMetricsRequest(metrics: [IMPRESSIONS])
    }

    static AdsMetricsRequest 'all ctr'() {
        new AdsMetricsRequest(metrics: [CTR])
    }

    static AdsMetricsRequest 'all metrics, Google Ads'() {
        new AdsMetricsRequest(
                metrics: [CLICKS, IMPRESSIONS, CTR],
                filters: [(DATASOURCE): 'Google Ads']
        )
    }

    static AdsMetricsRequest 'all metrics, Twitter Ads'() {
        new AdsMetricsRequest(
                metrics: [CLICKS, IMPRESSIONS, CTR],
                filters: [(DATASOURCE): 'Twitter Ads']
        )
    }

    static AdsMetricsRequest 'all metrics, Twitter Ads, SN_Kistenkonkurrenz'() {
        new AdsMetricsRequest(
                metrics: [CLICKS, IMPRESSIONS, CTR],
                filters: [
                        (DATASOURCE): 'Twitter Ads',
                        (CAMPAIGN)  : 'SN_Kistenkonkurrenz'
                ]
        )
    }

    static AdsMetricsRequest 'all metrics between 01_12_19 and 31_12_19'() {
        new AdsMetricsRequest(
                metrics: [CLICKS, IMPRESSIONS, CTR],
                dateRange: new AdsMetricsRequest.DateRange(
                        from: OffsetDateTime.parse('2019-12-01T00:00:00+00:00'),
                        to: OffsetDateTime.parse('2019-12-31T00:00:00+00:00'),
                )
        )
    }

    static AdsMetricsRequest 'all metrics Facebook Ads, Versicherungen, 01_12_19 and 10_12_19'() {
        new AdsMetricsRequest(
                metrics: [CLICKS, IMPRESSIONS, CTR],
                filters: [
                        (DATASOURCE): 'Facebook Ads',
                        (CAMPAIGN)  : 'Versicherungen'
                ],
                dateRange: new AdsMetricsRequest.DateRange(
                        from: OffsetDateTime.parse('2019-12-01T00:00:00+00:00'),
                        to: OffsetDateTime.parse('2019-12-10T00:00:00+00:00')
                )
        )
    }

    static AdsMetricsRequest 'all metrics, group by datasource'() {
        new AdsMetricsRequest(
                metrics: [CLICKS, IMPRESSIONS, CTR],
                groupBy: [DATASOURCE]
        )
    }

    static AdsMetricsRequest 'all metrics, group by campaign'() {
        new AdsMetricsRequest(
                metrics: [CLICKS, IMPRESSIONS, CTR],
                groupBy: [CAMPAIGN]
        )
    }

    static AdsMetricsRequest 'all metrics, group by datasource and campaign'() {
        new AdsMetricsRequest(
                metrics: [CLICKS, IMPRESSIONS, CTR],
                groupBy: [DATASOURCE, CAMPAIGN]
        )
    }

    static AdsMetricsRequest 'all metrics, group by datasource and campaign, Touristik Routenplaner'() {
        new AdsMetricsRequest(
                metrics: [CLICKS, IMPRESSIONS, CTR],
                groupBy: [DATASOURCE, CAMPAIGN],
                filters: [(CAMPAIGN): 'Touristik Routenplaner']
        )
    }

    static AdsMetricsRequest 'all metrics, group by datasource and campaign, Google Ads'() {
        new AdsMetricsRequest(
                metrics: [CLICKS, IMPRESSIONS, CTR],
                groupBy: [DATASOURCE, CAMPAIGN],
                filters: [(DATASOURCE): 'Google Ads']
        )
    }

    static AdsMetricsRequest 'all metrics, group by datasource and campaign, Google Ads, 01_12_19 and 15_12_19'() {
        new AdsMetricsRequest(
                metrics: [CLICKS, IMPRESSIONS, CTR],
                groupBy: [DATASOURCE, CAMPAIGN],
                filters: [(DATASOURCE): 'Google Ads'],
                dateRange: new AdsMetricsRequest.DateRange(
                        from: OffsetDateTime.parse('2019-12-01T00:00:00+00:00'),
                        to: OffsetDateTime.parse('2019-12-15T00:00:00+00:00')
                )
        )
    }

    // Invalid requests
    static AdsMetricsRequest 'request without metrics provided'() {
        new AdsMetricsRequest(metrics: ['non-supported'])
    }

    static AdsMetricsRequest 'request with non-supported metric'() {
        new AdsMetricsRequest(metrics: ['non-supported'])
    }

    static AdsMetricsRequest 'request with non-supported dimension in groupBy'() {
        new AdsMetricsRequest(metrics: [CLICKS], groupBy: ['non-supported'])
    }

    static AdsMetricsRequest 'request with non-supported dimension in filter'() {
        new AdsMetricsRequest(metrics: [CLICKS], filters: ['non-supported': 'value'])
    }

    static AdsMetricsRequest 'request with invalid date range (from is missing)'() {
        new AdsMetricsRequest(
                metrics: [CLICKS],
                dateRange: new AdsMetricsRequest.DateRange(
                        from: null,
                        to: OffsetDateTime.parse('2019-12-01T00:00:00+00:00')
                )
        )
    }

    static AdsMetricsRequest 'request with invalid date range (to is missing)'() {
        new AdsMetricsRequest(
                metrics: [CLICKS],
                dateRange: new AdsMetricsRequest.DateRange(
                        from: OffsetDateTime.parse('2019-12-01T00:00:00+00:00'),
                        to: null
                )
        )
    }

    // -- Responses
    static AdsMetricsResponse 'totals without entries'() {
        new AdsMetricsResponse(new AdsMetricsResponse.Totals(2882253 as BigInteger, 51047032 as BigInteger, 5.65 as Double), [])
    }

    static AdsMetricsResponse 'totals with clicks only'() {
        new AdsMetricsResponse(new AdsMetricsResponse.Totals(2882253 as BigInteger, null, null), [])
    }

    static AdsMetricsResponse 'totals with impressions only'() {
        new AdsMetricsResponse(new AdsMetricsResponse.Totals(null, 51047032 as BigInteger, null), [])
    }

    static AdsMetricsResponse 'totals with ctr only'() {
        new AdsMetricsResponse(new AdsMetricsResponse.Totals(null, null, 5.65 as Double), [])
    }

    static AdsMetricsResponse 'totals for Google Ads'() {
        new AdsMetricsResponse(new AdsMetricsResponse.Totals(64806 as BigInteger, 27061130 as BigInteger, 0.24 as Double), [])
    }

    static AdsMetricsResponse 'totals for Twitter Ads'() {
        new AdsMetricsResponse(new AdsMetricsResponse.Totals(2644871 as BigInteger, 17879651 as BigInteger, 14.79 as Double), [])
    }

    static AdsMetricsResponse 'totals for Twitter Ads, SN_Kistenkonkurrenz'() {
        new AdsMetricsResponse(new AdsMetricsResponse.Totals(86 as BigInteger, 5619 as BigInteger, 1.53 as Double), [])
    }

    static AdsMetricsResponse 'totals for period 01_12_19 and 31_12_19'() {
        new AdsMetricsResponse(new AdsMetricsResponse.Totals(220848 as BigInteger, 5141846 as BigInteger, 4.30 as Double), [])
    }

    static AdsMetricsResponse 'totals for Facebook Ads, Versicherungen, 01_12_19 and 10_12_19'() {
        new AdsMetricsResponse(new AdsMetricsResponse.Totals(16 as BigInteger, 90 as BigInteger, 17.78 as Double), [])
    }

    static AdsMetricsResponse 'response with totals and entries grouped by datasource'() {
        new AdsMetricsResponse(
                totals: new AdsMetricsResponse.Totals(2882253 as BigInteger, 51047032 as BigInteger, 5.65 as Double),
                entries: [
                        new AdsMetrics(172576 as BigInteger, 6106251 as BigInteger, 2.83 as Double, 'Facebook Ads', null),
                        new AdsMetrics(64806 as BigInteger, 27061130 as BigInteger, 0.24 as Double, 'Google Ads', null),
                        new AdsMetrics(2644871 as BigInteger, 17879651 as BigInteger, 14.79 as Double, 'Twitter Ads', null)
                ]
        )
    }

    // Totals
    static AdsMetricsResponse.Totals 'totals for all campaigns and data sources'() {
        new AdsMetricsResponse.Totals(2882253 as BigInteger, 51047032 as BigInteger, 5.65 as Double)
    }

    static AdsMetricsResponse.Totals 'totals for all datasource, Touristik Routenplaner'() {
        new AdsMetricsResponse.Totals(733866 as BigInteger, 4144672 as BigInteger, 17.71 as Double)
    }

    static AdsMetricsResponse.Totals 'totals for all campaigns, Google Ads'() {
        new AdsMetricsResponse.Totals(64806 as BigInteger, 27061130 as BigInteger, 0.24 as Double)
    }

    static AdsMetricsResponse.Totals 'totals for all campaigns, Google Ads, 01_12_19 and 15_12_19'() {
        new AdsMetricsResponse.Totals(3464 as BigInteger, 1401536 as BigInteger, 0.25 as Double)
    }
}
