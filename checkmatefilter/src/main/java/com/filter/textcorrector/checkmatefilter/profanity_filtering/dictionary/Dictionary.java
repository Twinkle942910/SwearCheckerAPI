package com.filter.textcorrector.checkmatefilter.profanity_filtering.dictionary;

import java.util.Set;

public interface Dictionary {
    Set<String> search(String text);
    boolean isProfane(String phrase);
    int size();
}
