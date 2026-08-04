package com.example.distributed_search_engine.indexing.storage;

import java.nio.file.Path;

public final class SegmentFileNamingStrategy {

    private SegmentFileNamingStrategy() {
    }

    public static String fileName(
            long segmentId
    ) {

        return "segment_%05d.idx".formatted(segmentId);

    }

    public static long extractSegmentId(
            Path path
    ) {

        String fileName =
                path.getFileName().toString();

        String id = fileName
                .replace("segment_", "")
                .replace(".idx", "");

        return Long.parseLong(id);

    }

}