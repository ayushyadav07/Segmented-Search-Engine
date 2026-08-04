package com.example.distributed_search_engine.indexing.index;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Posting {

    private final UUID documentId;

    private final List<Integer> positions = new ArrayList<>();

    public Posting(UUID documentId) {
        this.documentId = documentId;
    }

    public UUID getDocumentId() {
        return documentId;
    }

    public List<Integer> getPositions() {
        return positions;
    }

    public void addPosition(int position) {
        positions.add(position);
    }

    public int getTermFrequency() {
        return positions.size();
    }
}