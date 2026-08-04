package com.example.distributed_search_engine.indexing.builder;

import com.example.distributed_search_engine.indexing.analyzer.Token;
import com.example.distributed_search_engine.indexing.analyzer.TokenStream;
import com.example.distributed_search_engine.indexing.index.Posting;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class IndexBuilder {

    public void index(
            UUID documentId,
            TokenStream tokenStream,
            InMemorySegment segment
    ) {

        Map<String, Posting> postingMap = new HashMap<>();

        for (Token token : tokenStream.tokens()) {

            Posting posting = postingMap.computeIfAbsent(
                    token.term(),
                    ignored -> new Posting(documentId)
            );

            posting.addPosition(token.position());

        }

        postingMap.forEach(segment::addPosting);

    }

}