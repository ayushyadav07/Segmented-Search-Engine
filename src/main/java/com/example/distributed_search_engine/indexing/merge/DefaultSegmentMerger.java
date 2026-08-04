package com.example.distributed_search_engine.indexing.merge;

import com.example.distributed_search_engine.indexing.builder.InMemorySegment;
import com.example.distributed_search_engine.indexing.storage.BinarySegmentReader;
import com.example.distributed_search_engine.indexing.storage.BinarySegmentWriter;
import com.example.distributed_search_engine.indexing.storage.SegmentManager;
import com.example.distributed_search_engine.indexing.storage.SegmentMetadata;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Component
public class DefaultSegmentMerger
        implements SegmentMerger {

    private final SegmentManager segmentManager;

    private final BinarySegmentReader reader;

    private final BinarySegmentWriter writer;

    private final MergePolicy mergePolicy;

    public DefaultSegmentMerger(
            SegmentManager segmentManager,
            BinarySegmentReader reader,
            BinarySegmentWriter writer,
            MergePolicy mergePolicy
    ) {

        this.segmentManager = segmentManager;
        this.reader = reader;
        this.writer = writer;
        this.mergePolicy = mergePolicy;

    }

    @Override
    public void merge() {

        List<SegmentMetadata> segments =
                List.copyOf(
                        mergePolicy.selectSegments(
                                segmentManager.getSegments()
                        )
                );

        if (segments.isEmpty()) {
            return;
        }

        InMemorySegment merged =
                new InMemorySegment();

        for (SegmentMetadata metadata :
                segments) {

            merged.merge(
                    reader.readSegment(metadata)
            );

        }

        SegmentMetadata mergedMetadata = writer.write(merged);
        segmentManager.replaceSegments(
                segments,
                mergedMetadata
        );
        deleteSegments(segments);

    }

    private void deleteSegments(
            List<SegmentMetadata> segments
    ) {

        for (SegmentMetadata metadata : segments) {

            try {

                Files.deleteIfExists(
                        metadata.path()
                );

            } catch (IOException e) {

                throw new RuntimeException(
                        "Unable to delete segment "
                                + metadata.segmentId(),
                        e
                );

            }

        }

    }

}