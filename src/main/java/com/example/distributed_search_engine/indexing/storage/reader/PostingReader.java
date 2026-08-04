package com.example.distributed_search_engine.indexing.storage.reader;

import com.example.distributed_search_engine.indexing.index.Posting;
import com.example.distributed_search_engine.indexing.index.PostingList;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

public class PostingReader {

    public PostingList read(
            RandomAccessFile raf
    ) throws IOException {


        System.out.println(
                "Posting reader pointer: "
                        + raf.getFilePointer()
        );


        int postingCount =
                raf.readInt();


        System.out.println(
                "Posting count: "
                        + postingCount
        );


        PostingList postingList =
                new PostingList();


        for (int i = 0; i < postingCount; i++) {


            UUID documentId =
                    new UUID(
                            raf.readLong(),
                            raf.readLong()
                    );


            Posting posting =
                    new Posting(documentId);


            int positionCount =
                    raf.readInt();


            System.out.println(
                    "Document: "
                            + documentId
                            + " positions: "
                            + positionCount
            );


            for (int j = 0; j < positionCount; j++) {


                posting.addPosition(
                        raf.readInt()
                );

            }


            postingList.addPosting(posting);

        }


        return postingList;

    }

}