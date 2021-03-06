package com.filter.textcorrector.api.controller;

import com.filter.textcorrector.api.dto.CensoredText;
import com.filter.textcorrector.api.dto.CheckedText;
import com.filter.textcorrector.api.dto.CompoundSuggestions;
import com.filter.textcorrector.api.dto.PreproccessedText;
import com.filter.textcorrector.api.dto.Profane;
import com.filter.textcorrector.api.dto.Valid;
import com.filter.textcorrector.api.dto.WordSuggestions;
import com.filter.textcorrector.api.exception.InvalidParameterException;
import com.filter.textcorrector.api.service.TextFilterService;
import com.filter.textcorrector.checkmatefilter.profanity_filtering.model.Censored;
import com.filter.textcorrector.checkmatefilter.spellchecking.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

//http://localhost:8080/swearchecker/censor?text=hello%20fucking%20world&language=english
@RestController
@RequestMapping("/swearchecker")
public class TextFilterController {
    @Autowired
    @Qualifier("filterService")
    private TextFilterService textFilterService;

    @RequestMapping(value = "censor", method = RequestMethod.GET,
            consumes = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE},
            produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public CensoredText censor(@RequestParam("text") String text,
                               @RequestParam("language") String language,
                               @RequestParam(value = "replacement", required = false, defaultValue = "") String replacement,
                               @RequestParam(value = "preproccessing", required = false) boolean doPreproccessing,
                               @RequestParam(value = "remove_repeated_letters", required = false) boolean removeRepeatedLetters,
                               @RequestParam(value = "check_for_compounds", required = false) boolean checkForCompounds,
                               @RequestParam(value = "remove", required = false) boolean removeOrReplace,
                               @RequestParam(value = "spellcheck", required = false) boolean doSpellcheck) throws InvalidParameterException {

        if (!Language.contains(language)) {
            throw new InvalidParameterException("There is no such language - " + language);
        }

        return new CensoredText(text, textFilterService.censor(text, Language.fromString(language), replacement, doPreproccessing,
                removeRepeatedLetters, checkForCompounds, removeOrReplace, doSpellcheck));
    }

    @RequestMapping(value = "is_profane", method = RequestMethod.GET,
            consumes = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE},
            produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public Profane isProfane(@RequestParam("text") String text,
                             @RequestParam("language") String language) throws InvalidParameterException {

        if (!Language.contains(language)) {
            throw new InvalidParameterException("There is no such language - " + language);
        }

        return new Profane(text, textFilterService.isProfane(text, Language.fromString(language)));
    }

    @RequestMapping(value = "check_word", method = RequestMethod.GET,
            consumes = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE},
            produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public WordSuggestions checkWord(@RequestParam("text") String text,
                                     @RequestParam("language") String language,
                                     @RequestParam(value = "preproccessing", required = false) boolean doPreproccessing,
                                     @RequestParam(value = "remove_repeated_letters", required = false) boolean removeRepeatedLetters,
                                     @RequestParam(value = "match_percentage", required = false, defaultValue = "0.0") float maxMatchPercentage,
                                     @RequestParam(value = "suggestion_limit", required = false, defaultValue = "0") int suggestionLimit)
            throws InvalidParameterException {

        if (text.split(" ").length > 1) {
            throw new InvalidParameterException("It cannot be more than 1 word here. Number of words = " + text.split(" ").length);
        }

        if (!Language.contains(language)) {
            throw new InvalidParameterException("There is no such language - " + language);
        }

        return new WordSuggestions(text, textFilterService.checkWord(text, Language.fromString(language), doPreproccessing,
                removeRepeatedLetters, maxMatchPercentage, suggestionLimit));
    }

    @RequestMapping(value = "check_compound", method = RequestMethod.GET,
            consumes = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE},
            produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public CompoundSuggestions checkCompound(@RequestParam("text") String text,
                                             @RequestParam("language") String language,
                                             @RequestParam(value = "preproccessing", required = false) boolean doPreproccessing,
                                             @RequestParam(value = "remove_repeated_letters", required = false) boolean removeRepeatedLetters,
                                             @RequestParam(value = "match_percentage", required = false, defaultValue = "0.0") float maxMatchPercentage,
                                             @RequestParam(value = "suggestion_limit", required = false, defaultValue = "0") int suggestionLimit)
            throws InvalidParameterException {

        if (!Language.contains(language)) {
            throw new InvalidParameterException("There is no such language - " + language);
        }

        return new CompoundSuggestions(text, textFilterService.checkCompound(text, Language.fromString(language), doPreproccessing,
                removeRepeatedLetters, maxMatchPercentage, suggestionLimit));
    }

    @RequestMapping(value = "check_text", method = RequestMethod.GET,
            consumes = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE},
            produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public CheckedText checkText(@RequestParam("text") String text,
                                 @RequestParam("language") String language,
                                 @RequestParam(value = "preproccessing", required = false) boolean doPreproccessing,
                                 @RequestParam(value = "remove_repeated_letters", required = false) boolean removeRepeatedLetters,
                                 @RequestParam(value = "check_for_compounds", required = false) boolean checkForCompounds,
                                 @RequestParam(value = "keep_unrecognized", required = false, defaultValue = "true") boolean keepUnrecognized,
                                 @RequestParam(value = "match_percentage", required = false, defaultValue = "0.0") float maxMatchPercentage,
                                 @RequestParam(value = "suggestion_limit", required = false, defaultValue = "0") int suggestionLimit)
            throws InvalidParameterException {

        if (!Language.contains(language)) {
            throw new InvalidParameterException("There is no such language - " + language);
        }

        return new CheckedText(text, textFilterService.checkText(text, Language.fromString(language), doPreproccessing,
                removeRepeatedLetters, checkForCompounds, keepUnrecognized, maxMatchPercentage, suggestionLimit));
    }

    @RequestMapping(value = "search_profanity", method = RequestMethod.GET,
            consumes = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE},
            produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public Censored searcForProfanity(@RequestParam("text") String text,
                                      @RequestParam("language") String language,
                                      @RequestParam(value = "replacement", required = false, defaultValue = "") String replacement,
                                      @RequestParam(value = "preproccessing", required = false) boolean doPreproccessing,
                                      @RequestParam(value = "remove_repeated_letters", required = false) boolean removeRepeatedLetters,
                                      @RequestParam(value = "check_for_compounds", required = false) boolean checkForCompounds,
                                      @RequestParam(value = "remove", required = false) boolean removeOrReplace,
                                      @RequestParam(value = "spellcheck", required = false) boolean doSpellcheck) throws InvalidParameterException {

        if (!Language.contains(language)) {
            throw new InvalidParameterException("There is no such language - " + language);
        }

        return textFilterService.searchForProfanity(text, Language.fromString(language), replacement, doPreproccessing,
                removeRepeatedLetters, checkForCompounds, removeOrReplace, doSpellcheck);
    }

    @RequestMapping(value = "preproccess", method = RequestMethod.GET,
            consumes = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE},
            produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public PreproccessedText preproccess(@RequestParam("text") String text,
                                         @RequestParam("language") String language,
                                         @RequestParam(value = "remove_repeated_letters", required = false) boolean removeRepeatedLetters)
            throws InvalidParameterException {

        if (!Language.contains(language)) {
            throw new InvalidParameterException("There is no such language - " + language);
        }

        return new PreproccessedText(text, textFilterService.preproccess(text, Language.fromString(language), removeRepeatedLetters));
    }

    @RequestMapping(value = "is_valid", method = RequestMethod.GET,
            consumes = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE},
            produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public Valid isValid(@RequestParam("text") String text,
                         @RequestParam("language") String language)
            throws InvalidParameterException {

        if (text.split(" ").length > 1) {
            throw new InvalidParameterException("It cannot be more than 1 word here. Number of words = " + text.split(" ").length);
        }

        if (!Language.contains(language)) {
            throw new InvalidParameterException("There is no such language - " + language);
        }

        return new Valid(text, textFilterService.isValid(text, Language.fromString(language)));
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Wrong language input")
    @ExceptionHandler(InvalidParameterException.class)
    public void languageParameterError() {
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Wrong type of text input")
    @ExceptionHandler(InvalidParameterException.class)
    public void wordError() {
    }
}
