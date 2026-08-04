package com.example.distributed_search_engine.indexing.analyzer;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class StopWordFilter {

    private static final Set<String> STOP_WORDS = Set.of(

            "a",
            "an",
            "the",
            "is",
            "are",
            "was",
            "were",
            "to",
            "of",
            "in",
            "on",
            "for",
            "with",
            "at",
            "by",
            "from"

    );

    public List<Token> filter(List<Token> tokens) {

        return tokens.stream()
                .filter(token -> !STOP_WORDS.contains(token.term()))
                .collect(Collectors.toList());

    }

}