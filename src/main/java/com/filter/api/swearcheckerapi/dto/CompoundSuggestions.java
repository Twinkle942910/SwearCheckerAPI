package com.filter.api.swearcheckerapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CompoundSuggestions {
    @JsonProperty("input_text")
    private String text;
    private List<String> suggestions;
}
