package com.filter.textcorrector.checkmatefilter.spellchecking.dictionary;

public class EnglishDictionary extends AbstractDictionary {
    private static final String DICTIONARY_PATH = "/dictionaries/en_dictionary_80k.txt";

    public EnglishDictionary() {
        super(DICTIONARY_PATH);
    }
}
