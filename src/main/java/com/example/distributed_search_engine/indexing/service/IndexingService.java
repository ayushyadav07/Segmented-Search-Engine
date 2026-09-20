package com.example.distributed_search_engine.indexing.service;

import com.example.distributed_search_engine.indexing.analyzer.Analyzer;
import com.example.distributed_search_engine.indexing.analyzer.TokenStream;
import com.example.distributed_search_engine.indexing.buffer.SegmentBuffer;
import com.example.distributed_search_engine.indexing.builder.IndexBuilder;
import com.example.distributed_search_engine.indexing.dto.IndexingResult;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IndexingService {

    private final Analyzer analyzer;
    private final IndexBuilder indexBuilder;
    private final SegmentBuffer segmentBuffer;

    public IndexingService(
            Analyzer analyzer,
            IndexBuilder indexBuilder,
            SegmentBuffer segmentBuffer
    ) {
        this.analyzer = analyzer;
        this.indexBuilder = indexBuilder;
        this.segmentBuffer = segmentBuffer;
    }

    public IndexingResult index(
            UUID documentId,
            String content
    ) {

        TokenStream tokenStream = analyzer.analyze(content);

        indexBuilder.index(
                documentId,
                tokenStream,
                segmentBuffer.currentSegment()
        );

        segmentBuffer.documentIndexed();

        return new IndexingResult(
                segmentBuffer.documentCount(),
                segmentBuffer.currentSegment().vocabularySize(),
                tokenStream.tokens().size()
        );

    }

    public void flush() {
        segmentBuffer.flush();
    }

}