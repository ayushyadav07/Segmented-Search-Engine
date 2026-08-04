package com.example.distributed_search_engine.search.statistics;

import com.example.distributed_search_engine.document.repository.DocumentRepository;
import org.springframework.stereotype.Service;

@Service
public class DefaultCollectionStatisticsService
        implements CollectionStatisticsService {

    private final DocumentRepository documentRepository;

    public DefaultCollectionStatisticsService(
            DocumentRepository documentRepository
    ) {
        this.documentRepository = documentRepository;
    }

    @Override
    public CollectionStatistics getStatistics() {

        return new CollectionStatistics(

                documentRepository.count(),

                0

        );

    }

}