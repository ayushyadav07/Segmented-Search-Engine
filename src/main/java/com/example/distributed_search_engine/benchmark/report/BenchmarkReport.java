package com.example.distributed_search_engine.benchmark.report;

import com.example.distributed_search_engine.benchmark.metrics.BenchmarkMetrics;
import org.springframework.stereotype.Component;

@Component
public class BenchmarkReport {

    public void print(
            BenchmarkMetrics metrics
    ) {

        System.out.println();
        System.out.println("========== INDEX BENCHMARK ==========");
        System.out.println();

        System.out.println("Documents           : " + metrics.getDocuments());
        System.out.println("Vocabulary Size     : " + metrics.getVocabularySize());
        System.out.println("Tokens              : " + metrics.getTokens());

        System.out.println();

        System.out.printf(
                "Index Time          : %.2f sec%n",
                metrics.getElapsedSeconds()
        );

        System.out.printf(
                "Docs/sec            : %.2f%n",
                metrics.getDocumentsPerSecond()
        );

        System.out.printf(
                "Tokens/sec          : %.2f%n",
                metrics.getTokensPerSecond()
        );

        System.out.println();

        System.out.println(
                "Segments            : "
                        + metrics.getSegmentCount()
        );

        System.out.println(
                "Index Size          : "
                        + metrics.getIndexSizeMB()
                        + " MB"
        );

        System.out.println();

        System.out.println(
                "Heap Before         : "
                        + metrics.getHeapBeforeMB()
        );

        System.out.println(
                "Heap After          : "
                        + metrics.getHeapAfterMB()
        );

        System.out.println();

        System.out.println("=====================================");
    }

}