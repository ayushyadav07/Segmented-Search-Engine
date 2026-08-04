package com.example.distributed_search_engine.search.posting;

import com.example.distributed_search_engine.indexing.index.PostingList;

import java.util.List;

public interface PostingListOperator {

    PostingList union(
            List<PostingList> postingLists
    );

    PostingList intersect(
            List<PostingList> postingLists
    );

}