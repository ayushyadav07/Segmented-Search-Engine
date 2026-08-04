package com.example.distributed_search_engine.indexing.buffer;

import com.example.distributed_search_engine.indexing.builder.InMemorySegment;
import com.example.distributed_search_engine.indexing.storage.BinarySegmentWriter;
import org.springframework.stereotype.Component;

@Component
public class SegmentBuffer {

    private final ActiveSegment activeSegment = new ActiveSegment();

    private final FlushPolicy flushPolicy;

    private final BinarySegmentWriter segmentWriter;

    public SegmentBuffer(
            FlushPolicy flushPolicy,
            BinarySegmentWriter segmentWriter
    ) {
        this.flushPolicy = flushPolicy;
        this.segmentWriter = segmentWriter;
    }

    public InMemorySegment currentSegment() {
        return activeSegment.getSegment();
    }

    public void documentIndexed() {

        activeSegment.incrementDocumentCount();

        if (flushPolicy.shouldFlush(activeSegment)) {

            flush();

        }

    }

    public synchronized void flush() {

        if (activeSegment.documentCount() == 0) {
            return;
        }

        segmentWriter.write(activeSegment.getSegment());

        activeSegment.reset();

    }

    public int documentCount() {
        return activeSegment.documentCount();
    }

}