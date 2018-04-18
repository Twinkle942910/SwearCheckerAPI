package com.filter.textcorrector.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Profane {
    @JsonProperty("input_text")
    private String text;

    @JsonProperty("profane")
    private boolean isProfane;
}
