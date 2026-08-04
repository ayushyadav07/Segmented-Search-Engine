package com.example.distributed_search_engine.indexing.buffer;

import org.springframework.stereotype.Component;

@Component
public class DocumentCountFlushPolicy implements FlushPolicy {

    private static final int MAX_DOCUMENTS = 1000;

    @Override
    public boolean shouldFlush(ActiveSegment segment) {

        return segment.documentCount() >= MAX_DOCUMENTS;

    }

}