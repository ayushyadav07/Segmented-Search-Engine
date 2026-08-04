package com.example.distributed_search_engine.document.repository;

import com.example.distributed_search_engine.document.entity.Document;
import com.example.distributed_search_engine.document.enums.DocumentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DocumentRepository extends JpaRepository<Document, UUID> {

    Optional<Document> findByUrl(String url);

    boolean existsByUrl(String url);

    List<Document> findAllByStatus(DocumentStatus status);

}