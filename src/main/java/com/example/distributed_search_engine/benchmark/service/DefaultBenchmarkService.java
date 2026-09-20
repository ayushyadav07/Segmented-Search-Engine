package com.example.distributed_search_engine.benchmark.service;

import com.example.distributed_search_engine.benchmark.document.BenchmarkDocument;
import com.example.distributed_search_engine.benchmark.document.SyntheticDocumentGenerator;
import com.example.distributed_search_engine.benchmark.indexing.BenchmarkIndexingService;
import com.example.distributed_search_engine.benchmark.metrics.BenchmarkMetrics;
import com.example.distributed_search_engine.indexing.storage.SegmentConstants;
import com.example.distributed_search_engine.indexing.storage.SegmentManager;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class DefaultBenchmarkService
        implements BenchmarkService {

    private final SyntheticDocumentGenerator generator;

    private final BenchmarkIndexingService indexingService;

    private final SegmentManager segmentManager;

    public DefaultBenchmarkService(

            SyntheticDocumentGenerator generator,

            BenchmarkIndexingService indexingService,

            SegmentManager segmentManager

    ) {

        this.generator = generator;
        this.indexingService = indexingService;
        this.segmentManager = segmentManager;

    }

    @Override
    public BenchmarkMetrics runIndexBenchmark(

            int documents,

            int vocabulary,

            int averageLength,

            long seed

    ) {

        BenchmarkMetrics metrics =
                new BenchmarkMetrics();

        List<BenchmarkDocument> dataset =

                generator.generate(

                        documents,

                        vocabulary,

                        averageLength,

                        seed

                );

        metrics.setDocuments(documents);

        metrics.setVocabularySize(vocabulary);

        long totalTokens = 0;

        Runtime runtime =
                Runtime.getRuntime();

        runtime.gc();

        try {

            Thread.sleep(100);

        }

        catch (InterruptedException e) {

            Thread.currentThread().interrupt();

        }

        metrics.setHeapBefore(

                runtime.totalMemory()

                        - runtime.freeMemory()

        );

        long start =
                System.nanoTime();

        for (BenchmarkDocument document :
                dataset) {

            totalTokens +=

                    document.content()

                            .split("\\s+")

                            .length;

            indexingService.index(
                    document
            );

        }

        long end =
                System.nanoTime();

        metrics.setHeapAfter(

                runtime.totalMemory()

                        - runtime.freeMemory()

        );

        metrics.setTokens(
                totalTokens
        );

        metrics.setElapsedNanos(
                end - start
        );

        metrics.setSegmentCount(

                segmentManager

                        .getSegments()

                        .size()

        );

        metrics.setIndexSizeBytes(

                calculateIndexSize()

        );

        return metrics;

    }

    private long calculateIndexSize() {

        try {

            return Files.walk(

                            Path.of(

                                    SegmentConstants.DIRECTORY

                            )

                    )

                    .filter(
                            Files::isRegularFile
                    )

                    .mapToLong(path -> {

                        try {

                            return Files.size(path);

                        }

                        catch (IOException e) {

                            return 0;

                        }

                    })

                    .sum();

        }

        catch (IOException e) {

            return 0;

        }

    }

}