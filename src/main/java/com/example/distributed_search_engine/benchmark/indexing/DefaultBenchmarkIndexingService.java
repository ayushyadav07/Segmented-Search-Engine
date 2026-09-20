package com.example.distributed_search_engine.benchmark.indexing;

import com.example.distributed_search_engine.benchmark.document.BenchmarkDocument;
import com.example.distributed_search_engine.indexing.service.IndexingService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DefaultBenchmarkIndexingService
        implements BenchmarkIndexingService {

    private final IndexingService indexingService;

    public DefaultBenchmarkIndexingService(
            IndexingService indexingService
    ) {
        this.indexingService = indexingService;
    }

    @Override
    public void index(
            BenchmarkDocument document
    ) {

        indexingService.index(
                UUID.randomUUID(),
                document.content()
        );

    }

}