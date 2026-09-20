package com.example.distributed_search_engine.document.repository;

import com.example.distributed_search_engine.document.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DocumentRepository extends JpaRepository<Document, UUID> {
    boolean existsByUrl(String url);
}