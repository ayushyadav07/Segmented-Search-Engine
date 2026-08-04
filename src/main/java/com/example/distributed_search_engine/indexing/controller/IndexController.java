package com.example.distributed_search_engine.indexing.controller;

import com.example.distributed_search_engine.indexing.dto.IndexingResult;
import com.example.distributed_search_engine.indexing.service.IndexingService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/index")
public class IndexController {

    private final IndexingService indexingService;

    public IndexController(IndexingService indexingService) {
        this.indexingService = indexingService;
    }

    @PostMapping("/{documentId}")
    public IndexingResult index(

            @PathVariable UUID documentId,

            @RequestBody String text

    ) {

        return indexingService.index(documentId, text);

    }

}