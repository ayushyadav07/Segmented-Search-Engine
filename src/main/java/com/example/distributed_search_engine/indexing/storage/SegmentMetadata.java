package com.example.distributed_search_engine.indexing.storage;

import java.nio.file.Path;

public record SegmentMetadata(

        long segmentId,

        Path path,

        int vocabularySize,

        long createdAt

) {
}