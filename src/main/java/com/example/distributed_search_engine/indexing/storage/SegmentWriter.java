package com.example.distributed_search_engine.indexing.storage;

import com.example.distributed_search_engine.indexing.builder.InMemorySegment;

public interface SegmentWriter {

    SegmentMetadata write(
            InMemorySegment segment
    );

}