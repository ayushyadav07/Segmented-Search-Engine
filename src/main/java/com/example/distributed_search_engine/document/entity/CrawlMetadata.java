package com.example.distributed_search_engine.document.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "crawl_metadata")
public class CrawlMetadata {

    @Id
    private UUID documentId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "document_id")
    private Document document;

    @Column(name = "crawl_depth")
    private Integer crawlDepth;

    @Column(name = "last_crawled_at")
    private Instant lastCrawledAt;

    @Column(name = "next_crawl_at")
    private Instant nextCrawlAt;

    @Column(name = "fetch_duration_ms")
    private Long fetchDurationMs;

    @Column(name = "http_status")
    private Integer httpStatus;

    @Column(length = 200)
    private String etag;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "retry_count")
    private Integer retryCount;

    public CrawlMetadata() {
    }

    public UUID getDocumentId() {
        return documentId;
    }

    public void setDocumentId(UUID documentId) {
        this.documentId = documentId;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Integer getCrawlDepth() {
        return crawlDepth;
    }

    public void setCrawlDepth(Integer crawlDepth) {
        this.crawlDepth = crawlDepth;
    }

    public Instant getLastCrawledAt() {
        return lastCrawledAt;
    }

    public void setLastCrawledAt(Instant lastCrawledAt) {
        this.lastCrawledAt = lastCrawledAt;
    }

    public Instant getNextCrawlAt() {
        return nextCrawlAt;
    }

    public void setNextCrawlAt(Instant nextCrawlAt) {
        this.nextCrawlAt = nextCrawlAt;
    }

    public Long getFetchDurationMs() {
        return fetchDurationMs;
    }

    public void setFetchDurationMs(Long fetchDurationMs) {
        this.fetchDurationMs = fetchDurationMs;
    }

    public Integer getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(Integer httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }
}