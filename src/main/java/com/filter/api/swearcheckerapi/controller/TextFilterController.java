package com.filter.api.swearcheckerapi.controller;

import com.filter.api.swearcheckerapi.exception.InvalidParameterException;
import com.filter.api.swearcheckerapi.service.TextFilterService;
import com.filter.textcorrector.spellchecking.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public String censor(@RequestParam("text") String text,
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

        return textFilterService.censor(text, Language.fromString(language), replacement, doPreproccessing,
                removeRepeatedLetters, checkForCompounds, removeOrReplace, doSpellcheck);
    }

    @RequestMapping(value = "is_profane", method = RequestMethod.GET,
            consumes = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE},
            produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public boolean isProfane(@RequestParam("text") String text,
                             @RequestParam("language") String language) throws InvalidParameterException {

        if (!Language.contains(language)) {
            throw new InvalidParameterException("There is no such language - " + language);
        }

        return textFilterService.isProfane(text, Language.fromString(language));
    }

    @RequestMapping(value = "check_word", method = RequestMethod.GET,
            consumes = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE},
            produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public List<String> checkWord(@RequestParam("text") String text,
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

        return textFilterService.checkWord(text, Language.fromString(language), doPreproccessing,
                removeRepeatedLetters, maxMatchPercentage, suggestionLimit);
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
