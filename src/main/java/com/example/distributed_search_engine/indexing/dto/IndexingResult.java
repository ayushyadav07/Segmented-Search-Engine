package com.example.distributed_search_engine.indexing.dto;

public record IndexingResult(

        long indexedDocuments,

        int vocabularySize,

        int tokenCount

) {
}