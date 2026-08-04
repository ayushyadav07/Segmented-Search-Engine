package com.example.distributed_search_engine.search.query;

import org.springframework.stereotype.Component;

@Component
public class QueryExecutorFactory {

    private final OrQueryExecutor orExecutor;

    private final AndQueryExecutor andExecutor;

    public QueryExecutorFactory(
            OrQueryExecutor orExecutor,
            AndQueryExecutor andExecutor
    ) {
        this.orExecutor = orExecutor;
        this.andExecutor = andExecutor;
    }

    public QueryExecutor getExecutor(
            QueryType type
    ) {

        return switch (type) {

            case OR -> orExecutor;

            case AND -> andExecutor;

        };

    }

}