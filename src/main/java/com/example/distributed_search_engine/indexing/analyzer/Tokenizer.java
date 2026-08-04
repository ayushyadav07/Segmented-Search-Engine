package com.example.distributed_search_engine.indexing.analyzer;

import java.util.List;

public interface Tokenizer {

    List<Token> tokenize(String text);

}