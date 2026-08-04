package com.example.distributed_search_engine.search;

import java.util.UUID;

public record SearchResult(

        UUID documentId,

        double score

) {
}