package com.averity.simple.warehouse.api

import com.averity.simple.warehouse.SimpleWarehouse
import com.averity.simple.warehouse.api.model.AdsMetricsResponse
import com.averity.simple.warehouse.support.MySQLContainerSupport
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import static com.averity.simple.warehouse.fixtures.AdsMetricsTestFixtures.*
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(classes = SimpleWarehouse, webEnvironment = RANDOM_PORT)
class WarehouseControllerSpec extends Specification {

    @LocalServerPort
    int serverPort

    @Shared
    RestTemplate restClient = new RestTemplate()

    @Shared
    MySQLContainerSupport mysql = new MySQLContainerSupport()

    def setupSpec() {
        mysql.startContainer()
    }

    def cleanupSpec() {
        mysql.stopContainer()
    }

    @Unroll
    def "'/ads/metrics' returns #expectedResponse when provided with #request without grouping"() {
        when:
        def actualResponse = restClient.postForEntity("http://localhost:$serverPort/api/warehouse/ads/metrics", request, AdsMetricsResponse)

        then:
        actualResponse.statusCode == HttpStatus.OK
        actualResponse.body.totals == expectedResponse.totals
        actualResponse.body.entries.empty

        where:
        request                                                             | expectedResponse
        // Metrics
        'all metrics'()                                                     | 'totals without entries'()
        'all clicks'()                                                      | 'totals with clicks only'()
        'all impressions'()                                                 | 'totals with impressions only'()
        'all ctr'()                                                         | 'totals with ctr only'()
        // Filtering
        'all metrics, Google Ads'()                                         | 'totals for Google Ads'()
        'all metrics, Twitter Ads'()                                        | 'totals for Twitter Ads'()
        'all metrics, Twitter Ads, SN_Kistenkonkurrenz'()                   | 'totals for Twitter Ads, SN_Kistenkonkurrenz'()
        // Date ranges
        'all metrics between 01_12_19 and 31_12_19'()                       | 'totals for period 01_12_19 and 31_12_19'()
        // Date range with filtering
        'all metrics Facebook Ads, Versicherungen, 01_12_19 and 10_12_19'() | 'totals for Facebook Ads, Versicherungen, 01_12_19 and 10_12_19'()
    }

    def "'/ads/metrics' returns #expectedTotals along with #expectedEntriesSize entries for grouping request #request"() {
        when:
        def actualResponse = restClient.postForEntity("http://localhost:$serverPort/api/warehouse/ads/metrics", request, AdsMetricsResponse)

        then:
        actualResponse.statusCode == HttpStatus.OK
        actualResponse.body.totals == expectedTotals
        actualResponse.body.entries.size() == expectedEntriesSize

        where:
        request                                                                              | expectedTotals                                                  | expectedEntriesSize
        'all metrics, group by datasource'()                                                 | 'totals for all campaigns and data sources'()                   | 3
        'all metrics, group by campaign'()                                                   | 'totals for all campaigns and data sources'()                   | 104
        'all metrics, group by datasource and campaign'()                                    | 'totals for all campaigns and data sources'()                   | 185
        'all metrics, group by datasource and campaign, Touristik Routenplaner'()            | 'totals for all datasource, Touristik Routenplaner'()           | 3
        'all metrics, group by datasource and campaign, Google Ads'()                        | 'totals for all campaigns, Google Ads'()                        | 17
        'all metrics, group by datasource and campaign, Google Ads, 01_12_19 and 15_12_19'() | 'totals for all campaigns, Google Ads, 01_12_19 and 15_12_19'() | 7
    }

    def "'/ads/metrics' returns totals along with grouped entries when at least one 'groupBy' field is provided"() {
        given:
        def request = 'all metrics, group by datasource'()

        when:
        def actualResponse = restClient.postForEntity("http://localhost:$serverPort/api/warehouse/ads/metrics", request, AdsMetricsResponse)

        then:
        def expectedResponse = 'response with totals and entries grouped by datasource'()
        actualResponse.statusCode == HttpStatus.OK
        actualResponse.body.totals == expectedResponse.totals
        actualResponse.body.entries.containsAll(expectedResponse.entries)
    }

    def "'/ads/metrics' returns 400 response for invalid query request: #invalidRequest"() {
        when:
        restClient.postForEntity("http://localhost:$serverPort/api/warehouse/ads/metrics", invalidRequest, AdsMetricsResponse)

        then:
        thrown(HttpClientErrorException.BadRequest)

        where:
        invalidRequest << [
                'request without metrics provided'(),
                'request with non-supported metric'(),
                'request with non-supported dimension in groupBy'(),
                'request with non-supported dimension in filter'(),
                'request with invalid date range (from is missing)'()
        ]
    }
}
