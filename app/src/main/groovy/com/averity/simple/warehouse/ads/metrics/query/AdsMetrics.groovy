package com.averity.simple.warehouse.ads.metrics.query

import groovy.transform.Immutable

@Immutable
class AdsMetrics {
    BigInteger clicks
    BigInteger impressions
    Double ctr
    String datasource
    String campaign
}
