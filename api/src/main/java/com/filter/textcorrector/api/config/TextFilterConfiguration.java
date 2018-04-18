package com.filter.textcorrector.api.config;

import com.filter.textcorrector.checkmatefilter.Filter;
import com.filter.textcorrector.checkmatefilter.TextFilter;
import com.filter.textcorrector.checkmatefilter.spellchecking.Language;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TextFilterConfiguration {
    @Bean
    public Filter textFilter(){
        return new TextFilter(Language.ENGLISH);
    }
}
