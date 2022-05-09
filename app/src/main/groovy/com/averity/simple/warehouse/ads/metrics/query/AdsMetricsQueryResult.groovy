package com.averity.simple.warehouse.ads.metrics.query

import groovy.transform.Immutable

import static com.averity.simple.warehouse.ads.metrics.AdsCustomMetrics.ctr

@Immutable
class AdsMetricsQueryResult {

    BigInteger totalClicks
    BigInteger totalImpressions
    Double totalCtr
    List<AdsMetrics> selectedEntries

    static Accumulator accumulator() {
        new Accumulator()
    }

    static class Accumulator {

        private BigInteger totalClicks
        private BigInteger totalImpressions
        private List<AdsMetrics> selectedEntries = []

        void nextEntry(AdsMetrics entry) {
            totalClicks = accumulate(totalClicks, entry.clicks)
            totalImpressions = accumulate(totalImpressions, entry.impressions)
            selectedEntries << entry
        }

        ResultBuilder toQueryResult() {
            new ResultBuilder(this)
        }

        private BigInteger accumulate(BigInteger target, BigInteger source) {
            if (!source) {
                return target
            }
            target ? target.add(source) : source
        }
    }

    static class ResultBuilder {

        private boolean includeEntries
        private boolean calculateCtr
        private Accumulator accumulator

        ResultBuilder(Accumulator accumulator) {
            this.accumulator = accumulator
        }

        ResultBuilder includeEntries(boolean includeEntries) {
            this.includeEntries = includeEntries
            this
        }

        ResultBuilder calculateCtr(boolean calculateCtr) {
            this.calculateCtr = calculateCtr
            this
        }

        AdsMetricsQueryResult get() {
            def totalCtr = calculateCtr ? extractOrCalculateCtr() : null
            def entries = includeEntries ? accumulator.selectedEntries : []
            new AdsMetricsQueryResult(accumulator.totalClicks, accumulator.totalImpressions, totalCtr, entries)
        }

        private Double extractOrCalculateCtr() {
            if (accumulator.selectedEntries.size() == 1) {
                accumulator.selectedEntries.first().ctr
            } else {
                ctr(accumulator.totalClicks, accumulator.totalImpressions)
            }
        }
    }
}
