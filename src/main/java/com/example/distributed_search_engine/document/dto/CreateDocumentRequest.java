package com.example.distributed_search_engine.document.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateDocumentRequest(

        @NotBlank
        String url

) {
}