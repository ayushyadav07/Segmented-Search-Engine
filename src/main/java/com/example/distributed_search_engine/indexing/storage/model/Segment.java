package com.example.distributed_search_engine.indexing.storage.model;

import com.example.distributed_search_engine.indexing.builder.InMemorySegment;
import com.example.distributed_search_engine.indexing.storage.SegmentMetadata;

public class Segment {

    private final SegmentMetadata metadata;
    private final InMemorySegment inMemorySegment;

    public Segment(
            SegmentMetadata metadata,
            InMemorySegment inMemorySegment
    ) {
        this.metadata = metadata;
        this.inMemorySegment = inMemorySegment;
    }

    public SegmentMetadata getMetadata() {
        return metadata;
    }

    public InMemorySegment getInMemorySegment() {
        return inMemorySegment;
    }
}