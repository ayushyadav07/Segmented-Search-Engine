package com.example.distributed_search_engine.benchmark.document;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VocabularyGenerator {

    public List<String> generate(

            int vocabularySize

    ) {

        List<String> vocabulary =
                new ArrayList<>(vocabularySize);

        for (int i = 0; i < vocabularySize; i++) {

            vocabulary.add(
                    "term_" + i
            );

        }

        return vocabulary;

    }

}