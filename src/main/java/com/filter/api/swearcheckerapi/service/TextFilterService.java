package com.filter.api.swearcheckerapi.service;

import com.filter.textcorrector.profanity_filtering.model.Censored;
import com.filter.textcorrector.spellchecking.Language;

import java.util.List;
import java.util.Set;

public interface TextFilterService {
    boolean isProfane(String phrase, Language language);

    List<String> checkWord(String word, Language language, boolean doPreproccessing,
                           boolean removeRepeatedLetters, float maxMatchPercentage, int suggestionLimit);

    List<String> checkCompound(String compound, Language language, boolean doPreproccessing,
                         boolean removeRepeatedLetters, float maxMatchPercentage, int suggestionLimit);

    String checkText(String text, Language language, boolean doPreproccessing,
                     boolean removeRepeatedLetters, boolean checkForCompounds,
                     boolean keepUnrecognized, float maxMatchPercentage, int suggestionLimit);

    String censor(String text, Language language, String replacement, boolean doPreproccessing,
                  boolean removeRepeatedLetters, boolean checkForCompounds,
                  boolean removeOrReplace, boolean doSpellcheck);

    Censored searchForProfanity(String text, Language language, String replacement, boolean doPreproccessing,
                                boolean removeRepeatedLetters, boolean checkForCompounds,
                                boolean removeOrReplace, boolean doSpellcheck);

    String preproccess(String text, Language language, boolean removeRepeatedLetters);

    boolean isValid(String word, Language language);

    void addToBlackList(String word, Language language);

    void removeFromBlackList(String word, Language language);

    Set<String> getBlackList();

    void addToWhiteList(String word, Language language);

    void removeFromWhiteList(String word, Language language);

    Set<String> getWhiteList();
}
