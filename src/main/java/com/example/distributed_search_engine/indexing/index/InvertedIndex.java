package com.example.distributed_search_engine.indexing.index;

import com.example.distributed_search_engine.indexing.analyzer.TokenStream;

import java.util.Optional;
import java.util.UUID;

public interface InvertedIndex {

    void index(UUID documentId, TokenStream stream);

    Optional<PostingList> getPostingList(String term);

}