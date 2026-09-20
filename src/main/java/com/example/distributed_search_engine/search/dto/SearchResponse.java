package com.example.distributed_search_engine.search.dto;

import com.example.distributed_search_engine.search.SearchResult;

import java.util.List;

public record SearchResponse(

        String query,

        int totalHits,

        List<SearchResult> documents

) {
}