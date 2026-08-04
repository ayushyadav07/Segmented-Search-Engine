package com.example.distributed_search_engine.search.segment;

import com.example.distributed_search_engine.indexing.index.PostingList;
import com.example.distributed_search_engine.indexing.storage.BinarySegmentReader;
import com.example.distributed_search_engine.indexing.storage.SegmentHeader;
import com.example.distributed_search_engine.indexing.storage.SegmentMetadata;
import com.example.distributed_search_engine.indexing.storage.writer.DictionaryEntry;
import org.springframework.stereotype.Component;

import java.io.RandomAccessFile;
import java.util.Map;

@Component
public class DefaultSegmentSearcher
        implements SegmentSearcher {

    private final BinarySegmentReader segmentReader;

    public DefaultSegmentSearcher(
            BinarySegmentReader segmentReader
    ) {
        this.segmentReader = segmentReader;
    }

    @Override
    public TermSearchResult search(
            SegmentMetadata metadata,
            String term
    ) {

        try (
                RandomAccessFile raf =
                        segmentReader.open(
                                metadata.path()
                        )
        ) {

            SegmentHeader header =
                    segmentReader.readHeader(raf);

            Map<String, DictionaryEntry> dictionary =
                    segmentReader.readDictionary(raf);

            DictionaryEntry entry =
                    dictionary.get(term);

            if (entry == null) {
                return null;
            }

            long absoluteOffset =
                    header.postingAreaOffset()
                            + entry.postingOffset();

            PostingList postingList =
                    segmentReader.readPostingList(
                            raf,
                            absoluteOffset
                    );

            return new TermSearchResult(
                    postingList,
                    entry.documentFrequency()
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to search segment "
                            + metadata.segmentId(),
                    e
            );

        }

    }

}