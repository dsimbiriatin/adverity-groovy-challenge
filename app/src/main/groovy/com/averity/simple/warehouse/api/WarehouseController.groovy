package com.averity.simple.warehouse.api

import com.averity.simple.warehouse.api.model.AdsMetricsRequest
import com.averity.simple.warehouse.api.model.AdsMetricsResponse
import com.averity.simple.warehouse.ads.metrics.AdsMetricsService
import groovy.util.logging.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

import javax.validation.Valid

@Slf4j
@RestController
@RequestMapping('/api/warehouse')
class WarehouseController {

    private final AdsMetricsService service

    WarehouseController(AdsMetricsService service) {
        this.service = service
    }

    @PostMapping('ads/metrics')
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<AdsMetricsResponse> queryAdsMetrics(@Valid @RequestBody AdsMetricsRequest request) {
        log.info('Ads metrics query request: {}', request)
        def queryResult = service.queryAndAccumulate(request)
        ResponseEntity.ok(AdsMetricsResponse.of(queryResult))
    }
}
