package com.example.distributed_search_engine.document.service.impl;

import com.example.distributed_search_engine.crawler.model.WebPage;
import com.example.distributed_search_engine.crawler.service.CrawlerService;
import com.example.distributed_search_engine.document.dto.CreateDocumentRequest;
import com.example.distributed_search_engine.document.dto.DocumentResponse;
import com.example.distributed_search_engine.document.entity.Document;
import com.example.distributed_search_engine.document.enums.DocumentStatus;
import com.example.distributed_search_engine.document.repository.DocumentRepository;
import com.example.distributed_search_engine.document.service.DocumentService;
import com.example.distributed_search_engine.indexing.merge.SegmentMerger;
import com.example.distributed_search_engine.indexing.service.IndexingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository repository;

    private final CrawlerService crawlerService;

    private final IndexingService indexingService;

    private final SegmentMerger segmentMerger;

    public DocumentServiceImpl(
            DocumentRepository repository,
            CrawlerService crawlerService,
            IndexingService indexingService,
            SegmentMerger segmentMerger
    ) {
        this.repository = repository;
        this.crawlerService = crawlerService;
        this.indexingService = indexingService;
        this.segmentMerger = segmentMerger;
    }

    @Override
    public DocumentResponse create(CreateDocumentRequest request) {

        if (repository.existsByUrl(request.url())) {
            throw new IllegalArgumentException("Document already exists");
        }

        Document document = new Document();

        document.setUrl(request.url());
        document.setStatus(DocumentStatus.DISCOVERED);

        repository.save(document);

        WebPage page = crawlerService.crawl(
                request.url()
        );

        indexingService.index(
                document.getId(),
                page.content()
        );
        document.setStatus(DocumentStatus.INDEXED);
        repository.save(document);

        indexingService.flush();

        segmentMerger.merge();


        return new DocumentResponse(
                document.getId(),
                document.getUrl(),
                document.getStatus(),
                document.getCreatedAt(),
                document.getUpdatedAt()
        );
    }
}