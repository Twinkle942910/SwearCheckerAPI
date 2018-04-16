package com.filter.api.swearcheckerapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CheckedText {
    @JsonProperty("input_text")
    private String text;

    @JsonProperty("checked_text")
    private String checkedText;
}
