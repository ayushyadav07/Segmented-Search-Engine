package com.example.distributed_search_engine.indexing.storage.writer;

import com.example.distributed_search_engine.indexing.index.Posting;
import com.example.distributed_search_engine.indexing.index.PostingList;

import java.io.DataOutputStream;
import java.io.IOException;

public class PostingWriter {

    public long writePostingList(
            DataOutputStream out,
            PostingList postingList
    ) throws IOException {

        long bytesWritten = 0;

        out.writeInt(postingList.getPostings().size());
        bytesWritten += Integer.BYTES;

        for (Posting posting : postingList.getPostings()) {

            out.writeLong(
                    posting.getDocumentId().getMostSignificantBits()
            );

            out.writeLong(
                    posting.getDocumentId().getLeastSignificantBits()
            );

            bytesWritten += Long.BYTES * 2;

            out.writeInt(
                    posting.getPositions().size()
            );

            bytesWritten += Integer.BYTES;

            for (Integer position : posting.getPositions()) {

                out.writeInt(position);

                bytesWritten += Integer.BYTES;

            }

        }

        return bytesWritten;

    }

}