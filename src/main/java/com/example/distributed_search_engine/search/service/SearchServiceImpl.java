package com.example.distributed_search_engine.search.service;

import com.example.distributed_search_engine.indexing.analyzer.Analyzer;
import com.example.distributed_search_engine.indexing.analyzer.Token;
import com.example.distributed_search_engine.indexing.analyzer.TokenStream;
import com.example.distributed_search_engine.indexing.index.Posting;
import com.example.distributed_search_engine.indexing.index.PostingList;
import com.example.distributed_search_engine.indexing.storage.SegmentMetadata;
import com.example.distributed_search_engine.indexing.storage.SegmentManager;
import com.example.distributed_search_engine.search.SearchIndexResult;
import com.example.distributed_search_engine.search.SearchResult;
import com.example.distributed_search_engine.search.dto.SearchResponse;
import com.example.distributed_search_engine.search.query.QueryExecutionResult;
import com.example.distributed_search_engine.search.query.QueryExecutorFactory;
import com.example.distributed_search_engine.search.query.QueryType;
import com.example.distributed_search_engine.search.ranking.Ranker;
import com.example.distributed_search_engine.search.segment.SegmentSearcher;
import com.example.distributed_search_engine.search.segment.TermSearchResult;
import com.example.distributed_search_engine.search.statistics.CollectionStatistics;
import com.example.distributed_search_engine.search.statistics.CollectionStatisticsService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SearchServiceImpl implements SearchService {

    private final Analyzer analyzer;

    private final SegmentManager segmentManager;

    private final SegmentSearcher segmentSearcher;

    private final Ranker ranker;

    private final QueryExecutorFactory queryExecutorFactory;

    private final CollectionStatisticsService collectionStatisticsService;

    public SearchServiceImpl(
            Analyzer analyzer,
            SegmentManager segmentManager,
            SegmentSearcher segmentSearcher,
            QueryExecutorFactory queryExecutorFactory,
            Ranker ranker,
            CollectionStatisticsService collectionStatisticsService
    ) {
        this.analyzer = analyzer;
        this.segmentManager = segmentManager;
        this.segmentSearcher = segmentSearcher;
        this.queryExecutorFactory = queryExecutorFactory;
        this.ranker = ranker;
        this.collectionStatisticsService = collectionStatisticsService;
    }

    @Override
    public SearchResponse search(

            String query,

            QueryType type

    ) {

        TokenStream stream =
                analyzer.analyze(query);

        if (stream.tokens().isEmpty()) {

            return new SearchResponse(
                    query,
                    0,
                    List.of()
            );

        }

        SearchIndexResult indexResult = new SearchIndexResult();

        for (Token token : stream.tokens()) {

            PostingList merged =
                    new PostingList();

            int documentFrequency = 0;

            for (SegmentMetadata metadata :
                    segmentManager.getSegments()) {

                TermSearchResult result =
                        segmentSearcher.search(
                                metadata,
                                token.term()
                        );

                if (result == null) {
                    continue;
                }

                documentFrequency +=
                        result.documentFrequency();

                for (Posting posting :
                        result.postingList().getPostings()) {

                    merged.addPosting(
                            posting
                    );

                }

            }

            indexResult.addTerm(

                    token.term(),

                    new TermSearchResult(

                            merged,

                            documentFrequency

                    )

            );

        }

        QueryExecutionResult executionResult =
                queryExecutorFactory
                        .getExecutor(type)
                        .execute(
                                indexResult.getTerms()
                        );

        CollectionStatistics statistics = collectionStatisticsService.getStatistics();

        List<SearchResult> ranked =
                ranker.rank(

                        executionResult,

                        indexResult.getTerms(),

                        statistics

                );

        return new SearchResponse(

                query,

                ranked.size(),

                ranked

        );

    }
}