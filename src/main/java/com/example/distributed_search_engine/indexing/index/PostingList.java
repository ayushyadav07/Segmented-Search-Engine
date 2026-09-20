package com.example.distributed_search_engine.indexing.index;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PostingList {

    private final Map<UUID, Posting> postings =
            new ConcurrentHashMap<>();

    public void addPosting(Posting posting) {

        postings.put(
                posting.getDocumentId(),
                posting
        );

    }

    public Collection<Posting> getPostings() {

        return postings.values();

    }

    public Posting getPosting(UUID documentId) {

        return postings.get(documentId);

    }

    public int getDocumentFrequency() {

        return postings.size();

    }

}