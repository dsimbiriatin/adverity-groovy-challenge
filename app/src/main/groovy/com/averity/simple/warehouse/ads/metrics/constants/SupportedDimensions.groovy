package com.averity.simple.warehouse.ads.metrics.constants

final class SupportedDimensions {

    static final String CAMPAIGN = 'campaign'
    static final String DATASOURCE = 'datasource'
    static final Set<String> ALL = [CAMPAIGN, DATASOURCE].asImmutable() as Set
}
