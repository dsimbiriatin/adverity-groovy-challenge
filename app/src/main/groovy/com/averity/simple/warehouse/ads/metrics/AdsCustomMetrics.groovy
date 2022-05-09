package com.averity.simple.warehouse.ads.metrics

import org.springframework.lang.Nullable

import static java.math.MathContext.*
import static java.math.RoundingMode.HALF_EVEN

final class AdsCustomMetrics {

    private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100)

    @Nullable
    static Double ctr(@Nullable BigInteger clicks, @Nullable BigInteger impressions) {
        if (!clicks || !impressions) {
            return null
        }
        clicks.toBigDecimal().divide(impressions.toBigDecimal(), DECIMAL128)
                .multiply(ONE_HUNDRED)
                .setScale(2, HALF_EVEN)
                .doubleValue()
    }
}
