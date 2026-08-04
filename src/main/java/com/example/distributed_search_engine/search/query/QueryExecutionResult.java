package com.example.distributed_search_engine.search.query;

import java.util.Map;
import java.util.UUID;

public record QueryExecutionResult(

        Map<UUID, DocumentMatch> matches

) {
}