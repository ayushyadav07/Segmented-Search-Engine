package com.example.distributed_search_engine.search.ranking;

import com.example.distributed_search_engine.search.SearchResult;
import com.example.distributed_search_engine.search.query.QueryExecutionResult;
import com.example.distributed_search_engine.search.segment.TermSearchResult;
import com.example.distributed_search_engine.search.statistics.CollectionStatistics;

import java.util.List;
import java.util.Map;

public interface Ranker {

    List<SearchResult> rank(

            QueryExecutionResult executionResult,

            Map<String, TermSearchResult> termResults,

            CollectionStatistics statistics

    );

}