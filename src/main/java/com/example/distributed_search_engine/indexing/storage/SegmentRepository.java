package com.example.distributed_search_engine.indexing.storage;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Component
public class SegmentRepository {

    private final Map<Long, SegmentMetadata> segments =
            new ConcurrentHashMap<>();

    @PostConstruct
    public void initialize() {

        try {

            Files.createDirectories(
                    Path.of(SegmentConstants.DIRECTORY)
            );

            try (Stream<Path> files = Files.list(
                    Path.of(SegmentConstants.DIRECTORY)
            )) {

                files.filter(Files::isRegularFile)
                        .filter(this::isSegmentFile)
                        .sorted(Comparator.comparing(Path::toString))
                        .forEach(this::registerExistingSegment);

            }

        } catch (IOException e) {

            throw new IllegalStateException(
                    "Unable to initialize SegmentRepository",
                    e
            );

        }

    }

    public void register(
            SegmentMetadata metadata
    ) {

        segments.put(
                metadata.segmentId(),
                metadata
        );

    }

    public Collection<SegmentMetadata> allSegments() {

        return segments.values();

    }

    private boolean isSegmentFile(
            Path path
    ) {

        String name = path.getFileName().toString();

        return name.startsWith(SegmentConstants.PREFIX)
                && name.endsWith(SegmentConstants.EXTENSION);

    }

    private void registerExistingSegment(
            Path path
    ) {

        long id = extractId(path);

        segments.put(
                id,
                new SegmentMetadata(
                        id,
                        path,
                        0,
                        Files.exists(path)
                                ? path.toFile().length()
                                : 0
                )
        );

    }

    private long extractId(
            Path path
    ) {

        String name = path.getFileName().toString();

        return Long.parseLong(
                name.replace(
                                SegmentConstants.PREFIX,
                                ""
                        )
                        .replace(
                                SegmentConstants.EXTENSION,
                                ""
                        )
        );

    }

}