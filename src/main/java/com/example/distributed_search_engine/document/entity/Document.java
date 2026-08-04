package com.example.distributed_search_engine.document.entity;

import com.example.distributed_search_engine.document.enums.DocumentStatus;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "documents")
public class Document {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true, length = 2048)
    private String url;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentStatus status;

    @Version
    private Long version;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @PrePersist
    void prePersist() {

        if (id == null) {
            id = UUID.randomUUID();
        }

        createdAt = Instant.now();
        updatedAt = Instant.now();
    }

    @PreUpdate
    void preUpdate() {
        updatedAt = Instant.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public DocumentStatus getStatus() {
        return status;
    }

    public void setStatus(DocumentStatus status) {
        this.status = status;
    }

    public Long getVersion() {
        return version;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}