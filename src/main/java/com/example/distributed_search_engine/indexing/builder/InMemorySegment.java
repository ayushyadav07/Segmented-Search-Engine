package com.example.distributed_search_engine.indexing.builder;

import com.example.distributed_search_engine.indexing.index.Posting;
import com.example.distributed_search_engine.indexing.index.PostingList;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class InMemorySegment {

    private final Map<String, PostingList> dictionary =
            new ConcurrentHashMap<>();

    public void addPosting(String term, Posting posting) {

        dictionary
                .computeIfAbsent(term, ignored -> new PostingList())
                .addPosting(posting);

    }

    public PostingList getPostingList(String term) {

        return dictionary.get(term);

    }

    public Set<String> terms() {

        return dictionary.keySet();

    }

    public int vocabularySize() {

        return dictionary.size();

    }

    public void addPostingList(
            String term,
            PostingList postingList
    ) {

        dictionary.put(
                term,
                postingList
        );

    }

    public void merge(
            InMemorySegment other
    ) {

        for (String term : other.terms()) {

            PostingList otherPostingList =
                    other.getPostingList(term);

            PostingList currentPostingList =
                    dictionary.get(term);

            if (currentPostingList == null) {

                dictionary.put(
                        term,
                        otherPostingList
                );

                continue;
            }

            for (Posting posting :
                    otherPostingList.getPostings()) {

                currentPostingList.addPosting(
                        posting
                );

            }

        }

    }

}