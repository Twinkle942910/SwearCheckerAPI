package com.filter.textcorrector.checkmatefilter.spellchecking.dictionary;

public class RussianDictionary extends AbstractDictionary {
    private static final String DICTIONARY_PATH = "/dictionaries/ru_dictionary_130k.txt";

    public RussianDictionary() {
        super(DICTIONARY_PATH);
    }
}
