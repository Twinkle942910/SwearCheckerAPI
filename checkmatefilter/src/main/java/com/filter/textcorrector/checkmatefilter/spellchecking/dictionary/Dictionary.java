package com.filter.textcorrector.checkmatefilter.spellchecking.dictionary;

import com.filter.textcorrector.checkmatefilter.spellchecking.model.Suggestion;

import java.util.List;

public interface Dictionary {
    boolean contains(String word);
    List<Suggestion> search(String word, float editDistancePercent);
    int getSize();
}
