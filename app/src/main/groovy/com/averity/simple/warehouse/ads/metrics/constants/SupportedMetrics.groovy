package com.averity.simple.warehouse.ads.metrics.constants

final class SupportedMetrics {

    static final String CTR = 'ctr'
    static final String CLICKS = 'clicks'
    static final String IMPRESSIONS = 'impressions'
    static final Set<String> ALL = [CTR, CLICKS, IMPRESSIONS].asImmutable() as Set
}
