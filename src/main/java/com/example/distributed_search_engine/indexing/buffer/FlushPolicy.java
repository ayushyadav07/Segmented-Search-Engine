package com.example.distributed_search_engine.indexing.buffer;

public interface FlushPolicy {

    boolean shouldFlush(ActiveSegment activeSegment);

}