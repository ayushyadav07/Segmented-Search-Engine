package com.example.distributed_search_engine.indexing.storage.writer;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class DictionaryWriter {

    public void write(

            DataOutputStream out,

            List<DictionaryEntry> entries

    ) throws IOException {

        out.writeInt(entries.size());

        for (DictionaryEntry entry : entries) {

            byte[] bytes =
                    entry.term().getBytes(StandardCharsets.UTF_8);

            out.writeInt(bytes.length);

            out.write(bytes);

            out.writeLong(entry.postingOffset());

            out.writeInt(entry.documentFrequency());

        }

    }

}