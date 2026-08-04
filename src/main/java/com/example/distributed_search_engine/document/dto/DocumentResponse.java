package com.example.distributed_search_engine.document.dto;

import com.example.distributed_search_engine.document.enums.DocumentStatus;

import java.time.Instant;
import java.util.UUID;

public record DocumentResponse(

        UUID id,

        String url,

        DocumentStatus status,

        Instant createdAt,

        Instant updatedAt

) {
}