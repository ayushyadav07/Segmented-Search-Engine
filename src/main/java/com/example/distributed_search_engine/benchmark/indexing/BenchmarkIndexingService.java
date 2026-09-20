package com.example.distributed_search_engine.benchmark.indexing;

import com.example.distributed_search_engine.benchmark.document.BenchmarkDocument;

public interface BenchmarkIndexingService {

    void index(
            BenchmarkDocument document
    );

}