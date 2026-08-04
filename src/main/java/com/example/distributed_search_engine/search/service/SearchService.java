package com.example.distributed_search_engine.search.service;

import com.example.distributed_search_engine.search.dto.SearchResponse;
import com.example.distributed_search_engine.search.query.QueryType;

public interface SearchService {

    SearchResponse search(

            String query,

            QueryType type

    );
}