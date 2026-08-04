package com.example.distributed_search_engine.indexing.storage;

public final class SegmentConstants {

    private SegmentConstants() {
    }

    public static final int MAGIC = 0x53454731;

    public static final int VERSION = 1;

    public static final String DIRECTORY = "data/segments";

    public static final String PREFIX = "segment_";

    public static final String EXTENSION = ".idx";

    public static final int HEADER_SIZE =
            Integer.BYTES +   // magic
                    Integer.BYTES +   // version
                    Long.BYTES +      // segment id
                    Long.BYTES +      // created at
                    Integer.BYTES +   // vocabulary size
                    Long.BYTES;      // posting area offset

}