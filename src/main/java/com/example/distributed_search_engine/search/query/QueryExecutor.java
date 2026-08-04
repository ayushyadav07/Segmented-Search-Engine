package com.example.distributed_search_engine.search.query;

import com.example.distributed_search_engine.search.segment.TermSearchResult;

import java.util.Map;

public interface QueryExecutor {

    QueryExecutionResult execute(

            Map<String, TermSearchResult> postings

    );

}