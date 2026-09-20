package com.example.distributed_search_engine.benchmark.controller;

import com.example.distributed_search_engine.benchmark.metrics.BenchmarkMetrics;
import com.example.distributed_search_engine.benchmark.report.BenchmarkReport;
import com.example.distributed_search_engine.benchmark.service.BenchmarkService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BenchmarkController {

    private final BenchmarkService benchmarkService;
    private final BenchmarkReport benchmarkReport;
    public BenchmarkController(
            BenchmarkService benchmarkService,
            BenchmarkReport benchmarkReport
    ) {

        this.benchmarkService = benchmarkService;
        this.benchmarkReport = benchmarkReport;
    }

    @GetMapping("/benchmark/index")
    public BenchmarkMetrics benchmark() {
        BenchmarkMetrics benchmark = benchmarkService.runIndexBenchmark(

                10000,

                50000,

                300,

                42

        );
        benchmarkReport.print(benchmark);
        return benchmark;

    }

}