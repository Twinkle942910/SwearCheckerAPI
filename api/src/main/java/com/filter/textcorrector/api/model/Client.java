package com.filter.textcorrector.api.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.ClientDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "oauth_client_details")
@Data
public class Client implements ClientDetails {
    @Id
    @Column(name = "client_id", nullable = false)
    private String clientId;
    @Column(name = "resource_ids")
    private String resourceIds;
    @Column(name = "client_secret", nullable = false)
    private String clientSecret;
    @Column(name = "scope", nullable = false)
    private String scope;
    @Column(name = "authorized_grant_types", nullable = false)
    private String grantTypes;
    @Column(name = "web_server_redirect_uri")
    private String webServerRedirectUri;
    @Column(name = "authorities", nullable = false)
    private String authorities;
    @Column(name = "access_token_validity", nullable = false)
    private Integer accessTokenValidity;
    @Column(name = "refresh_token_validity")
    private Long refreshTokenValidity;
    @Column(name = "additional_information", length = 4096)
    private String additionalInfo;
    @Column(name = "autoapprove")
    private String autoApprove;

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public Set<String> getResourceIds() {
        return Collections.singleton(resourceIds);
    }

    @Override
    public boolean isSecretRequired() {
        return false;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    @Override
    public boolean isScoped() {
        return false;
    }

    @Override
    public Set<String> getScope() {
        return new HashSet<>(Arrays.asList(scope.split("[^a-zA-Z0-9]")));
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        if(!grantTypes.isEmpty()) {
            return new HashSet<>(Arrays.asList(grantTypes.split(",")));
        }
        return new HashSet<>();
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return Collections.singleton(webServerRedirectUri);
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return new HashSet<>(AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return accessTokenValidity;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return Math.toIntExact(refreshTokenValidity);
    }

    @Override
    public boolean isAutoApprove(String s) {
        return Boolean.parseBoolean(autoApprove);
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        Map<String, Object> addInfo = new HashMap<>();
        Arrays.stream(additionalInfo.split("[^a-zA-Z0-9]")).forEach(info -> {
            String[] parts = info.split("=");
            addInfo.put(parts[0], parts[1]);
        });
        return addInfo;
    }
}