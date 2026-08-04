package com.example.distributed_search_engine.indexing.merge;

import com.example.distributed_search_engine.indexing.storage.SegmentMetadata;

import java.util.List;

public interface MergePolicy {

    List<SegmentMetadata> selectSegments(
            List<SegmentMetadata> segments
    );

}