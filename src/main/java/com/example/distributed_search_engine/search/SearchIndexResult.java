package com.example.distributed_search_engine.search;

import com.example.distributed_search_engine.search.segment.TermSearchResult;

import java.util.LinkedHashMap;
import java.util.Map;

public class SearchIndexResult {

    private final Map<String, TermSearchResult> terms =
            new LinkedHashMap<>();

    public void addTerm(
            String term,
            TermSearchResult result
    ) {

        terms.put(
                term,
                result
        );

    }

    public Map<String, TermSearchResult> getTerms() {
        return terms;
    }

}