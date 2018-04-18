package com.filter.textcorrector.api.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    private static final String RESOURCE_ID = "swearchecker-rest-api";
    private static final String SECURED_READ_SCOPE = "#oauth2.hasScope('read')";
    private static final String SECURED_WRITE_SCOPE = "#oauth2.hasScope('write')";
    private static final String SECURED_PATTERN = "/swearchecker/**";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.requestMatcher(new OAuthRequestedMatcher())
                .requestMatchers()
                .antMatchers(SECURED_PATTERN)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, SECURED_PATTERN).access(SECURED_WRITE_SCOPE)
                .anyRequest().access(SECURED_READ_SCOPE)
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .anyRequest().authenticated();
    }

    /**
     * Determine if the client request contained an OAuth Authorization (gives 401 code.)
     * Will autopromt authentication page to enter login and password
     */
    private static class OAuthRequestedMatcher implements RequestMatcher {
        public boolean matches(HttpServletRequest request) {
            String auth = request.getHeader("Authorization");
            boolean haveOauth2Token = (auth != null) && auth.startsWith("Bearer");
            boolean haveAccessToken = request.getParameter("access_token") != null;
            return haveOauth2Token || haveAccessToken;
        }
    }
}
