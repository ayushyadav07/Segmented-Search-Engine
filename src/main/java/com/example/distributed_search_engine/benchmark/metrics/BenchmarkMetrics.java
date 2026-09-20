package com.example.distributed_search_engine.benchmark.metrics;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BenchmarkMetrics {

    private long documents;

    private long tokens;

    /**
     * Configured vocabulary used by the generator.
     */
    private long vocabularySize;

    private long heapBefore;

    private long heapAfter;

    private long segmentCount;

    private long indexSizeBytes;

    /**
     * Total indexing time.
     */
    private long elapsedNanos;

    public long getElapsedMillis() {

        return elapsedNanos / 1_000_000;

    }

    public double getElapsedSeconds() {

        return elapsedNanos / 1_000_000_000.0;

    }

    public double getDocumentsPerSecond() {

        if (elapsedNanos == 0) {
            return 0;
        }

        return documents / getElapsedSeconds();

    }

    public double getTokensPerSecond() {

        if (elapsedNanos == 0) {
            return 0;
        }

        return tokens / getElapsedSeconds();

    }

    public double getAverageDocumentLength() {

        if (documents == 0) {
            return 0;
        }

        return (double) tokens / documents;

    }

    public double getHeapBeforeMB() {

        return heapBefore / (1024.0 * 1024);

    }

    public double getHeapAfterMB() {

        return heapAfter / (1024.0 * 1024);

    }

    public double getIndexSizeMB() {

        return indexSizeBytes / (1024.0 * 1024);

    }

}