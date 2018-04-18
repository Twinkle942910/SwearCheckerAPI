package com.filter.textcorrector.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Valid {
    private String word;
    @JsonProperty("valid")
    private boolean isValid;
}
