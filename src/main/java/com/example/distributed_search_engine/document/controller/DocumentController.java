package com.example.distributed_search_engine.document.controller;

import com.example.distributed_search_engine.document.dto.CreateDocumentRequest;
import com.example.distributed_search_engine.document.dto.DocumentResponse;
import com.example.distributed_search_engine.document.service.DocumentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/documents")
public class DocumentController {

    private final DocumentService service;

    public DocumentController(DocumentService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DocumentResponse create(

            @Valid
            @RequestBody
            CreateDocumentRequest request

    ) {

        return service.create(request);

    }

}