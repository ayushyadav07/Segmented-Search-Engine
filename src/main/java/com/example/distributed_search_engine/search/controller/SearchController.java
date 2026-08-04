package com.example.distributed_search_engine.search.controller;

import com.example.distributed_search_engine.indexing.merge.SegmentMerger;
import com.example.distributed_search_engine.search.dto.SearchResponse;
import com.example.distributed_search_engine.search.query.QueryType;
import com.example.distributed_search_engine.search.service.SearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {

    private final SearchService service;
    public SearchController(
            SearchService service
    ) {
        this.service = service;
    }

    @GetMapping("/search")
    public SearchResponse search(

            @RequestParam String q,

            @RequestParam(
                    defaultValue = "OR"
            )
            QueryType type

    ) {

        return service.search(
                q,
                type
        );
    }

}