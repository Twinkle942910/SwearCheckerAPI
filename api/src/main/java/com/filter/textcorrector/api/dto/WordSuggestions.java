package com.filter.textcorrector.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WordSuggestions {
    @JsonProperty("input_text")
    private String text;
    private List<String> suggestions;
}
