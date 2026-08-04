package com.example.distributed_search_engine.indexing.storage;

import com.example.distributed_search_engine.indexing.builder.InMemorySegment;
import com.example.distributed_search_engine.indexing.index.PostingList;
import com.example.distributed_search_engine.indexing.storage.reader.DictionaryReader;
import com.example.distributed_search_engine.indexing.storage.reader.PostingReader;
import com.example.distributed_search_engine.indexing.storage.writer.DictionaryEntry;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.util.Map;

@Component
public class BinarySegmentReader {

    private final DictionaryReader dictionaryReader =
            new DictionaryReader();

    private final PostingReader postingReader =
            new PostingReader();


    public SegmentHeader readHeader(
            RandomAccessFile raf
    ) throws IOException {


        long headerStart =
                raf.getFilePointer();


        int magic =
                raf.readInt();

        int version =
                raf.readInt();

        long segmentId =
                raf.readLong();

        long createdAt =
                raf.readLong();

        int vocabularySize =
                raf.readInt();

        long postingAreaOffset =
                raf.readLong();


        System.out.println("========== SEGMENT HEADER ==========");
        System.out.println("Header start       : " + headerStart);
        System.out.println("Magic              : " + magic);
        System.out.println("Version            : " + version);
        System.out.println("Segment Id         : " + segmentId);
        System.out.println("Created At         : " + createdAt);
        System.out.println("Vocabulary Size    : " + vocabularySize);
        System.out.println("Posting Area Offset: " + postingAreaOffset);
        System.out.println("Current Pointer    : " + raf.getFilePointer());
        System.out.println("====================================");


        return new SegmentHeader(
                magic,
                version,
                segmentId,
                createdAt,
                vocabularySize,
                postingAreaOffset
        );

    }


    public Map<String, DictionaryEntry> readDictionary(
            RandomAccessFile raf
    ) throws IOException {


        System.out.println(
                "Reading dictionary from position: "
                        + raf.getFilePointer()
        );


        Map<String, DictionaryEntry> dictionary =
                dictionaryReader.read(raf);


        System.out.println(
                "Dictionary size: "
                        + dictionary.size()
        );


        return dictionary;

    }


    public PostingList readPostingList(
            RandomAccessFile raf,
            long absoluteOffset
    ) throws IOException {


        System.out.println("========== POSTING READ ==========");
        System.out.println(
                "Requested offset : "
                        + absoluteOffset
        );

        System.out.println(
                "File length      : "
                        + raf.length()
        );


        raf.seek(absoluteOffset);


        System.out.println(
                "Pointer after seek: "
                        + raf.getFilePointer()
        );


        PostingList postingList =
                postingReader.read(raf);


        System.out.println(
                "Posting documents: "
                        + postingList.getDocumentFrequency()
        );


        System.out.println("==================================");


        return postingList;

    }


    public RandomAccessFile open(
            Path path
    ) throws IOException {


        System.out.println(
                "Opening segment file: "
                        + path
        );


        return new RandomAccessFile(
                path.toFile(),
                "r"
        );

    }

    public InMemorySegment readSegment(
            SegmentMetadata metadata
    ) {

        try (
                RandomAccessFile raf =
                        open(metadata.path())
        ) {

            SegmentHeader header =
                    readHeader(raf);

            Map<String, DictionaryEntry> dictionary =
                    readDictionary(raf);

            InMemorySegment segment =
                    new InMemorySegment();

            for (DictionaryEntry entry :
                    dictionary.values()) {

                long absoluteOffset =
                        header.postingAreaOffset()
                                + entry.postingOffset();

                PostingList postingList =
                        readPostingList(
                                raf,
                                absoluteOffset
                        );

                segment.addPostingList(
                        entry.term(),
                        postingList
                );

            }

            return segment;

        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable to read segment "
                            + metadata.segmentId(),
                    e
            );

        }

    }

}