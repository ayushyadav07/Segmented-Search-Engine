package com.example.distributed_search_engine.search.ranking;

import com.example.distributed_search_engine.search.segment.TermSearchResult;

import java.util.List;

public record SearchContext(

        List<TermSearchResult> termResults,

        long totalDocuments

) {
}