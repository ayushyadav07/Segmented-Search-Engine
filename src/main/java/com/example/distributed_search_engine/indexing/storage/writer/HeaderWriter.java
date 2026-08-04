package com.example.distributed_search_engine.indexing.storage.writer;

import com.example.distributed_search_engine.indexing.storage.SegmentHeader;

import java.io.DataOutputStream;
import java.io.IOException;

public class HeaderWriter {

    public void write(
            DataOutputStream out,
            SegmentHeader header
    ) throws IOException {

        out.writeInt(header.magic());

        out.writeInt(header.version());

        out.writeLong(header.segmentId());

        out.writeLong(header.createdAt());

        out.writeInt(header.vocabularySize());

        out.writeLong(header.postingAreaOffset());

    }

}