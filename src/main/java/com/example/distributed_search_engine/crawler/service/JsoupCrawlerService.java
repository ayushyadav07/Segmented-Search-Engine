package com.example.distributed_search_engine.crawler.service;

import com.example.distributed_search_engine.crawler.model.WebPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class JsoupCrawlerService implements CrawlerService {

    @Override
    public WebPage crawl(String url) {

        try {

            Document document = Jsoup
                    .connect(url)
                    .userAgent("DistributedSearchEngineBot/1.0")
                    .timeout(10000)
                    .get();

            String title = document.title();

            String content = document.body() == null
                    ? ""
                    : document.body().text();

            return new WebPage(
                    title,
                    content
            );

        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable to crawl: " + url,
                    e
            );

        }

    }

}