package com.example.distributed_search_engine.indexing.merge;

import com.example.distributed_search_engine.indexing.storage.SegmentMetadata;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class SizeTieredMergePolicy
        implements MergePolicy {

    private static final int MERGE_THRESHOLD = 5;

    @Override
    public List<SegmentMetadata> selectSegments(
            List<SegmentMetadata> segments
    ) {

        if (segments.size() < MERGE_THRESHOLD) {
            return Collections.emptyList();
        }

        return segments.subList(
                0,
                MERGE_THRESHOLD
        );

    }

}