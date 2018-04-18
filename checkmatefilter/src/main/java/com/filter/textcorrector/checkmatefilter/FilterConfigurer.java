package com.filter.textcorrector.checkmatefilter;

import com.filter.textcorrector.checkmatefilter.spellchecking.Language;

public interface FilterConfigurer {
    void doPreproccessing(boolean doPreproccessing);

    void doRemoveRepeatedLetters(boolean doRemoveRepeatedLetters);

    void doCheckCompounds(boolean doCheckCompounds);

    void setSuggestionLimit(int suggestionLimit);

    void keepUnrecognized(boolean keepUnrecognized);

    void changeLanguage(Language language);

    void setProfanityReplacement(String replacement);

    void doRemoveProfaneWord(boolean removeProfaneWord);

    void setMaxMatchPercentage(float maxMatchPercentage);
}
