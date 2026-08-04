package com.example.distributed_search_engine.search.ranking;

import com.example.distributed_search_engine.indexing.index.Posting;
import com.example.distributed_search_engine.search.SearchResult;
import com.example.distributed_search_engine.search.query.DocumentMatch;
import com.example.distributed_search_engine.search.query.QueryExecutionResult;
import com.example.distributed_search_engine.search.segment.TermSearchResult;
import com.example.distributed_search_engine.search.statistics.CollectionStatistics;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Component
public class TfIdfRanker
        implements Ranker {

    @Override
    public List<SearchResult> rank(

            QueryExecutionResult executionResult,

            Map<String, TermSearchResult> termResults,

            CollectionStatistics statistics


    ) {

        long totalDocuments = statistics.totalDocuments();

        List<SearchResult> results = new ArrayList<>();

        for (DocumentMatch match : executionResult.matches().values()) {
            double score = 0;

            for (Map.Entry<String, Posting> entry : match.getMatchedTerms().entrySet()) {
                String term = entry.getKey();
                Posting posting = entry.getValue();
                int tf = posting.getPositions().size();
                int df = termResults.get(term).documentFrequency();

                double idf = Math.log((double) totalDocuments / df);
                score += tf * idf;
            }
            results.add(new SearchResult(match.getDocumentId(), score));
        }

        results.sort(Comparator.comparingDouble(SearchResult::score).reversed());

        return results;
    }

}