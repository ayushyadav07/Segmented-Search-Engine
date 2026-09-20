package com.example.distributed_search_engine.benchmark.document;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class SyntheticDocumentGenerator {

    private final VocabularyGenerator vocabularyGenerator;

    public SyntheticDocumentGenerator(

            VocabularyGenerator vocabularyGenerator

    ) {

        this.vocabularyGenerator =
                vocabularyGenerator;

    }

    public List<BenchmarkDocument> generate(

            int documents,

            int vocabularySize,

            int averageLength,

            long seed

    ) {

        Random random =
                new Random(seed);

        List<String> vocabulary =
                vocabularyGenerator.generate(
                        vocabularySize
                );

        List<BenchmarkDocument> result =
                new ArrayList<>(documents);

        for (int i = 0; i < documents; i++) {

            StringBuilder content =
                    new StringBuilder();

            int length =

                    averageLength

                            + random.nextInt(101)

                            - 50;

            for (int j = 0; j < length; j++) {

                content.append(

                        vocabulary.get(

                                random.nextInt(
                                        vocabulary.size()
                                )

                        )

                ).append(' ');

            }

            result.add(

                    new BenchmarkDocument(

                            "Document " + i,

                            content.toString()

                    )

            );

        }

        return result;

    }

}