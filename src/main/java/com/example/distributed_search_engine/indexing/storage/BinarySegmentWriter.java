package com.example.distributed_search_engine.indexing.storage;

import com.example.distributed_search_engine.indexing.builder.InMemorySegment;
import com.example.distributed_search_engine.indexing.index.PostingList;
import com.example.distributed_search_engine.indexing.storage.writer.DictionaryEntry;
import com.example.distributed_search_engine.indexing.storage.writer.DictionaryWriter;
import com.example.distributed_search_engine.indexing.storage.writer.HeaderWriter;
import com.example.distributed_search_engine.indexing.storage.writer.PostingWriter;
import org.springframework.stereotype.Component;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
public class BinarySegmentWriter implements SegmentWriter {

    private final SegmentManager segmentManager;

    private final DictionaryWriter dictionaryWriter;

    private final PostingWriter postingWriter;

    private final HeaderWriter headerWriter = new HeaderWriter();

    public BinarySegmentWriter(
            SegmentManager segmentManager
    ) {
        this.segmentManager = segmentManager;
        this.dictionaryWriter = new DictionaryWriter();
        this.postingWriter = new PostingWriter();
    }

    @Override
    public SegmentMetadata write(
            InMemorySegment segment
    ) {

        long segmentId =
                segmentManager.allocateSegmentId();

        long createdAt =
                Instant.now().toEpochMilli();

        try {

            Files.createDirectories(
                    Path.of(SegmentConstants.DIRECTORY)
            );

            Path path = Path.of(
                    SegmentConstants.DIRECTORY,
                    SegmentFileNamingStrategy.fileName(segmentId)
            );


            ByteArrayOutputStream postingBuffer =
                    new ByteArrayOutputStream();


            try (
                    DataOutputStream postingOut =
                            new DataOutputStream(postingBuffer);

                    DataOutputStream out =
                            new DataOutputStream(
                                    new BufferedOutputStream(
                                            Files.newOutputStream(path)
                                    )
                            )
            ) {


                List<DictionaryEntry> dictionaryEntries =
                        buildDictionaryEntries(
                                segment,
                                postingOut
                        );


                long dictionarySize =
                        calculateDictionarySize(
                                dictionaryEntries
                        );


                long postingAreaOffset =
                        SegmentConstants.HEADER_SIZE
                                +
                                dictionarySize;


                SegmentHeader header =
                        new SegmentHeader(
                                SegmentConstants.MAGIC,
                                SegmentConstants.VERSION,
                                segmentId,
                                createdAt,
                                segment.vocabularySize(),
                                postingAreaOffset
                        );


                headerWriter.write(
                        out,
                        header
                );


                dictionaryWriter.write(
                        out,
                        dictionaryEntries
                );


                postingBuffer.writeTo(out);

            }


            SegmentMetadata metadata =
                    new SegmentMetadata(
                            segmentId,
                            path,
                            segment.vocabularySize(),
                            createdAt
                    );


            segmentManager.addSegment(metadata);


            return metadata;


        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable to write segment",
                    e
            );

        }

    }


    private List<DictionaryEntry> buildDictionaryEntries(
            InMemorySegment segment,
            DataOutputStream postingOut
    ) throws IOException {


        List<DictionaryEntry> entries =
                new ArrayList<>();

        long currentOffset = 0;


        for (String term : segment.terms()) {


            PostingList postingList =
                    segment.getPostingList(term);


            entries.add(
                    new DictionaryEntry(
                            term,
                            currentOffset,
                            postingList.getDocumentFrequency()
                    )
            );


            currentOffset +=
                    postingWriter.writePostingList(
                            postingOut,
                            postingList
                    );

        }


        return entries;

    }


    private long calculateDictionarySize(
            List<DictionaryEntry> entries
    ) {


        long size = Integer.BYTES;


        for (DictionaryEntry entry : entries) {


            byte[] termBytes =
                    entry.term()
                            .getBytes(StandardCharsets.UTF_8);


            /*
             * term length
             * term bytes
             * posting offset
             * document frequency
             */

            size += Integer.BYTES;
            size += termBytes.length;
            size += Long.BYTES;
            size += Integer.BYTES;

        }


        return size;

    }

}