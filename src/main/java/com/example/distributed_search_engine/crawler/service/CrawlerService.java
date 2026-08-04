package com.example.distributed_search_engine.crawler.service;

import com.example.distributed_search_engine.crawler.model.WebPage;

public interface CrawlerService {

    WebPage crawl(String url);

}