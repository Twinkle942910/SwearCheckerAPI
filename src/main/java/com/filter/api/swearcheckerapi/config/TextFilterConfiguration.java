package com.filter.api.swearcheckerapi.config;

import com.filter.textcorrector.Filter;
import com.filter.textcorrector.TextFilter;
import com.filter.textcorrector.spellchecking.Language;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TextFilterConfiguration {
    @Bean
    public Filter textFilter(){
        return new TextFilter(Language.ENGLISH);
    }
}
