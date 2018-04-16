package com.filter.api.swearcheckerapi.service;

import com.filter.textcorrector.Filter;
import com.filter.textcorrector.profanity_filtering.model.Censored;
import com.filter.textcorrector.spellchecking.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

//TODO: refactor these multiple parameters.
//TODO: implement white list and black list.
@PreAuthorize("hasAuthority('USER') || hasAuthority('USER_CLIENT')")
@Service("filterService")
public class TextFilterServiceImpl implements TextFilterService {
    @Autowired
    private Filter textFilter;

    @Override
    public boolean isProfane(String phrase, Language language) {
        changeLanguage(language);

        return textFilter.isProfane(phrase);
    }

    @Override
    public List<String> checkWord(String word, Language language, boolean doPreproccessing,
                                  boolean removeRepeatedLetters, float maxMatchPercentage, int suggestionLimit) {
        spellcheckConfig(language, doPreproccessing, removeRepeatedLetters, maxMatchPercentage, suggestionLimit);

        return textFilter.checkWord(word);
    }

    @Override
    public List<String> checkCompound(String compound, Language language, boolean doPreproccessing,
                                      boolean removeRepeatedLetters, float maxMatchPercentage, int suggestionLimit) {
        spellcheckConfig(language, doPreproccessing, removeRepeatedLetters, maxMatchPercentage, suggestionLimit);
        return textFilter.checkCompound(compound);
    }

    @Override
    public String checkText(String text, Language language, boolean doPreproccessing,
                            boolean removeRepeatedLetters, boolean checkForCompounds,
                            boolean keepUnrecognized, float maxMatchPercentage, int suggestionLimit) {

        changeLanguage(language);
        textFilter.doPreproccessing(doPreproccessing);
        textFilter.doRemoveRepeatedLetters(removeRepeatedLetters);
        textFilter.doCheckCompounds(checkForCompounds);
        if (maxMatchPercentage != 0.0) {
            textFilter.setMaxMatchPercentage(maxMatchPercentage);
        }

        if (suggestionLimit != 0) {
            textFilter.setSuggestionLimit(suggestionLimit);
        }
        textFilter.keepUnrecognized(keepUnrecognized);

        return textFilter.checkText(text);
    }

    @Override
    public String censor(String text, Language language, String replacement, boolean doPreproccessing,
                         boolean removeRepeatedLetters, boolean checkForCompounds, boolean removeOrReplace,
                         boolean doSpellcheck) {

        text = profanityFilterConfig(text, language, replacement, doPreproccessing, removeRepeatedLetters,
                checkForCompounds, removeOrReplace, doSpellcheck);
        return textFilter.censor(text);
    }


    @Override
    public Censored searchForProfanity(String text, Language language, String replacement,
                                       boolean doPreproccessing, boolean removeRepeatedLetters,
                                       boolean checkForCompounds, boolean removeOrReplace, boolean doSpellcheck) {

        text = profanityFilterConfig(text, language, replacement, doPreproccessing, removeRepeatedLetters,
                checkForCompounds, removeOrReplace, doSpellcheck);
        return textFilter.searchForProfanity(text);
    }

    @Override
    public String preproccess(String text, Language language, boolean removeRepeatedLetters) {
        changeLanguage(language);
        return textFilter.preproccess(text, removeRepeatedLetters);
    }

    @Override
    public boolean isValid(String word, Language language) {
        changeLanguage(language);
        return textFilter.isValid(word);
    }

    @Override
    public void addToBlackList(String word, Language language) {

    }

    @Override
    public void removeFromBlackList(String word, Language language) {

    }

    @Override
    public Set<String> getBlackList() {
        return null;
    }

    @Override
    public void addToWhiteList(String word, Language language) {

    }

    @Override
    public void removeFromWhiteList(String word, Language language) {

    }

    @Override
    public Set<String> getWhiteList() {
        return null;
    }

    private void changeLanguage(Language language) {
        if (textFilter.checkLanguage() != language) {
            textFilter.changeLanguage(language);
        }
    }

    private void spellcheckConfig(Language language, boolean doPreproccessing, boolean removeRepeatedLetters,
                                  float maxMatchPercentage, int suggestionLimit) {
        changeLanguage(language);
        textFilter.doPreproccessing(doPreproccessing);
        textFilter.doRemoveRepeatedLetters(removeRepeatedLetters);
        if (maxMatchPercentage != 0.0) {
            textFilter.setMaxMatchPercentage(maxMatchPercentage);
        }

        if (suggestionLimit != 0) {
            textFilter.setSuggestionLimit(suggestionLimit);
        }
    }

    private String profanityFilterConfig(String text, Language language, String replacement, boolean doPreproccessing,
                                         boolean removeRepeatedLetters, boolean checkForCompounds, boolean removeOrReplace,
                                         boolean doSpellcheck) {
        changeLanguage(language);
        textFilter.doPreproccessing(doPreproccessing);
        textFilter.doRemoveRepeatedLetters(removeRepeatedLetters);
        textFilter.doCheckCompounds(checkForCompounds);

        if (checkForCompounds) {
            text = textFilter.checkText(text);
        }

        textFilter.doRemoveProfaneWord(removeOrReplace);

        if (!replacement.equals("")) {
            textFilter.setProfanityReplacement(replacement);
        }

        if (doSpellcheck && !checkForCompounds) {
            text = textFilter.checkText(text);
        }
        return text;
    }
}
