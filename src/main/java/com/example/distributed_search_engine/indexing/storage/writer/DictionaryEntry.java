package com.example.distributed_search_engine.indexing.storage.writer;

public record DictionaryEntry(

        String term,

        long postingOffset,

        int documentFrequency

) {
}