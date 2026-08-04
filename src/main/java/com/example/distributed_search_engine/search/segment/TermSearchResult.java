package com.example.distributed_search_engine.search.segment;

import com.example.distributed_search_engine.indexing.index.PostingList;

public record TermSearchResult(

        PostingList postingList,

        int documentFrequency

) {
}