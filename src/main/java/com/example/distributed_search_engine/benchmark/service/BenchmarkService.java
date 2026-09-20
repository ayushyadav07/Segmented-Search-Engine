package com.example.distributed_search_engine.benchmark.service;

import com.example.distributed_search_engine.benchmark.metrics.BenchmarkMetrics;

public interface BenchmarkService {

    BenchmarkMetrics runIndexBenchmark(

            int documents,

            int vocabulary,

            int averageLength,

            long seed

    );

}