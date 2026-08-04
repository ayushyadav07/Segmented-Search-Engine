package com.example.distributed_search_engine.crawler;

import com.example.distributed_search_engine.crawler.model.WebPage;
import com.example.distributed_search_engine.crawler.service.CrawlerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CrawlerController {

    private final CrawlerService crawlerService;

    public CrawlerController(CrawlerService crawlerService) {
        this.crawlerService = crawlerService;
    }

    @GetMapping("/crawl")
    public WebPage crawl(
            @RequestParam String url
    ) {
        return crawlerService.crawl(url);
    }

}