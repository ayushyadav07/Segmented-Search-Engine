package com.example.distributed_search_engine.indexing.buffer;

import com.example.distributed_search_engine.indexing.builder.InMemorySegment;

import java.util.concurrent.atomic.AtomicInteger;

public class ActiveSegment {

    private InMemorySegment segment =
            new InMemorySegment();

    private final AtomicInteger documentCount =
            new AtomicInteger();

    public InMemorySegment getSegment() {

        return segment;

    }

    public void incrementDocumentCount() {

        documentCount.incrementAndGet();

    }

    public int documentCount() {

        return documentCount.get();

    }

    public void reset() {

        segment = new InMemorySegment();

        documentCount.set(0);

    }

}