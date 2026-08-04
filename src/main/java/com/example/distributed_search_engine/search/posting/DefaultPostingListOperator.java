package com.example.distributed_search_engine.search.posting;

import com.example.distributed_search_engine.indexing.index.Posting;
import com.example.distributed_search_engine.indexing.index.PostingList;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultPostingListOperator
        implements PostingListOperator {

    @Override
    public PostingList union(
            List<PostingList> postingLists
    ) {

        PostingList result = new PostingList();

        for (PostingList postingList : postingLists) {

            if (postingList == null) {
                continue;
            }

            for (Posting posting : postingList.getPostings()) {
                result.addPosting(posting);
            }
        }

        return result;

    }

    @Override
    public PostingList intersect(
            List<PostingList> postingLists
    ) {

        if (postingLists.isEmpty()) {
            return new PostingList();
        }

        PostingList first = postingLists.getFirst();

        PostingList result = new PostingList();

        outer:
        for (Posting posting : first.getPostings()) {

            for (int i = 1; i < postingLists.size(); i++) {

                PostingList current = postingLists.get(i);

                if (current == null
                        || current.getPosting(posting.getDocumentId()) == null) {
                    continue outer;
                }
            }

            result.addPosting(posting);
        }

        return result;
    }
}