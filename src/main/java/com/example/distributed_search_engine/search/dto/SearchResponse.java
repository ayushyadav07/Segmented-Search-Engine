package com.example.distributed_search_engine.search.dto;

import com.example.distributed_search_engine.search.SearchResult;

import java.util.List;
import java.util.UUID;

public record SearchResponse(

        String query,

        int totalHits,

        List<SearchResult> documents

) {
}