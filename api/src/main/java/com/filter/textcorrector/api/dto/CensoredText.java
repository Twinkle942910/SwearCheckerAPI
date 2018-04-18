package com.filter.textcorrector.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CensoredText {
    @JsonProperty("input_text")
    private String inputText;

    @JsonProperty("censored_text")
    private String censoredText;
}
