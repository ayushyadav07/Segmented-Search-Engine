package com.example.distributed_search_engine.search.query;

import com.example.distributed_search_engine.indexing.index.Posting;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class DocumentMatch {

    private final UUID documentId;

    private final Map<String, Posting> matchedTerms =
            new LinkedHashMap<>();

    public DocumentMatch(
            UUID documentId
    ) {
        this.documentId = documentId;
    }

    public UUID getDocumentId() {
        return documentId;
    }

    public Map<String, Posting> getMatchedTerms() {
        return matchedTerms;
    }

    public void addPosting(
            String term,
            Posting posting
    ) {

        matchedTerms.put(
                term,
                posting
        );

    }

}