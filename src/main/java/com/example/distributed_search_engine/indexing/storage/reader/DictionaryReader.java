package com.example.distributed_search_engine.indexing.storage.reader;

import com.example.distributed_search_engine.indexing.storage.writer.DictionaryEntry;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class DictionaryReader {

    public Map<String, DictionaryEntry> read(
            RandomAccessFile raf
    ) throws IOException {

        int entryCount = raf.readInt();

        Map<String, DictionaryEntry> dictionary =
                new HashMap<>(entryCount);

        for (int i = 0; i < entryCount; i++) {

            int length = raf.readInt();

            byte[] bytes = new byte[length];

            raf.readFully(bytes);

            String term =
                    new String(
                            bytes,
                            StandardCharsets.UTF_8
                    );

            long postingOffset =
                    raf.readLong();

            int documentFrequency =
                    raf.readInt();

            dictionary.put(

                    term,

                    new DictionaryEntry(
                            term,
                            postingOffset,
                            documentFrequency
                    )

            );

        }

        return dictionary;

    }

}