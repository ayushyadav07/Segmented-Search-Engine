package com.example.distributed_search_engine.indexing.analyzer;

import org.springframework.stereotype.Component;

@Component
public class LowerCaseNormalizer implements Normalizer {

    @Override
    public String normalize(String token) {

        return token.toLowerCase();

    }

}