package com.example.distributed_search_engine.indexing.storage;

public record SegmentHeader(

        int magic,

        int version,

        long segmentId,

        long createdAt,

        int vocabularySize,

        long postingAreaOffset

) {
}