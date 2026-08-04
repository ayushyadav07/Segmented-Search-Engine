package com.example.distributed_search_engine.search.query;

import com.example.distributed_search_engine.indexing.index.Posting;
import com.example.distributed_search_engine.indexing.index.PostingList;
import com.example.distributed_search_engine.search.segment.TermSearchResult;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class OrQueryExecutor
        implements QueryExecutor {

    @Override
    public QueryExecutionResult execute(

            Map<String, TermSearchResult> postings

    ) {

        Map<UUID, DocumentMatch> documents =
                new LinkedHashMap<>();

        for (Map.Entry<String, TermSearchResult> term :
                postings.entrySet()) {

            String queryTerm =
                    term.getKey();

            PostingList postingList =
                    term.getValue().postingList();

            for (Posting posting :
                    postingList.getPostings()) {

                DocumentMatch match =
                        documents.computeIfAbsent(

                                posting.getDocumentId(),

                                DocumentMatch::new

                        );

                match.addPosting(

                        queryTerm,

                        posting

                );

            }

        }

        return new QueryExecutionResult(
                documents
        );

    }

}