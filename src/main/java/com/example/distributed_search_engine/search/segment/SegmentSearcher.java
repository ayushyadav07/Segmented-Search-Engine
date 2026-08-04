package com.example.distributed_search_engine.search.segment;

import com.example.distributed_search_engine.indexing.storage.SegmentMetadata;

public interface SegmentSearcher {

    TermSearchResult search(
            SegmentMetadata metadata,
            String term
    );

}