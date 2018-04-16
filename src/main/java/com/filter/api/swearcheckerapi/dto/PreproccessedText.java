package com.filter.api.swearcheckerapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PreproccessedText {
    @JsonProperty("input_text")
    private String text;
    @JsonProperty("preproccessed_text")
    private String preproccessedText;
}
