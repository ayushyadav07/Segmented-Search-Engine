package com.example.distributed_search_engine.indexing.analyzer;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StandardTokenizer implements Tokenizer {

    @Override
    public List<Token> tokenize(String text) {

        List<Token> tokens = new ArrayList<>();

        if (text == null || text.isBlank()) {
            return tokens;
        }

        String[] words = text.split("[^A-Za-z0-9]+");

        int position = 0;

        for (String word : words) {

            if (word.isBlank()) {
                continue;
            }

            tokens.add(new Token(word, position++));

        }

        return tokens;

    }

}