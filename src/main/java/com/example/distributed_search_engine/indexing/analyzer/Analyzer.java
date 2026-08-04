package com.example.distributed_search_engine.indexing.analyzer;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Analyzer {

    private final Tokenizer tokenizer;

    private final Normalizer normalizer;

    private final StopWordFilter stopWordFilter;

    public Analyzer(
            Tokenizer tokenizer,
            Normalizer normalizer,
            StopWordFilter stopWordFilter
    ) {

        this.tokenizer = tokenizer;
        this.normalizer = normalizer;
        this.stopWordFilter = stopWordFilter;

    }

    public TokenStream analyze(String text) {

        List<Token> tokens = tokenizer.tokenize(text);

        List<Token> normalized = tokens.stream()
                .map(token -> new Token(
                        normalizer.normalize(token.term()),
                        token.position()))
                .toList();

        return new TokenStream(

                stopWordFilter.filter(normalized)

        );

    }

}