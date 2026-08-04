package com.example.distributed_search_engine.document.service;

import com.example.distributed_search_engine.document.dto.CreateDocumentRequest;
import com.example.distributed_search_engine.document.dto.DocumentResponse;

public interface DocumentService {

    DocumentResponse create(CreateDocumentRequest request);

}