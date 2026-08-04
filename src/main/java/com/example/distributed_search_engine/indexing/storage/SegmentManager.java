package com.example.distributed_search_engine.indexing.storage;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

@Component
public class SegmentManager {

    private final AtomicLong nextSegmentId =
            new AtomicLong();

    private final List<SegmentMetadata> segments =
            new CopyOnWriteArrayList<>();

    @PostConstruct
    public void initialize() {

        try {

            Files.createDirectories(
                    Path.of(SegmentConstants.DIRECTORY)
            );

            loadExistingSegments();

        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable to initialize SegmentManager",
                    e
            );

        }

    }

    public long allocateSegmentId() {

        return nextSegmentId.getAndIncrement();

    }

    public void addSegment(
            SegmentMetadata metadata
    ) {

        segments.add(metadata);

    }

    public List<SegmentMetadata> getSegments() {

        return List.copyOf(segments);

    }

    private void loadExistingSegments()
            throws IOException {

        try (Stream<Path> stream =
                     Files.list(
                             Path.of(
                                     SegmentConstants.DIRECTORY
                             ))) {

            List<Path> files = stream
                    .filter(Files::isRegularFile)
                    .sorted(Comparator.naturalOrder())
                    .toList();

            long maxId = -1;

            for (Path path : files) {

                long id =
                        SegmentFileNamingStrategy
                                .extractSegmentId(path);

                segments.add(
                        new SegmentMetadata(
                                id,
                                path,
                                -1,
                                Files.getLastModifiedTime(path)
                                        .toMillis()
                        )
                );

                maxId = Math.max(maxId, id);

            }

            nextSegmentId.set(maxId + 1);

        }

    }

    public synchronized void replaceSegments(
            List<SegmentMetadata> oldSegments,
            SegmentMetadata newSegment
    ) {

        segments.removeAll(oldSegments);

        segments.add(newSegment);

    }

}