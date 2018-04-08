package com.filter.api.swearcheckerapi.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "CLIENT", uniqueConstraints = {@UniqueConstraint(columnNames = {"CLIENT_ID"})})
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Client implements ClientDetails {
    private static final String RESOURCE_ID = "swearchecker-rest-api";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @Column(name = "CLIENT_ID", nullable = false)
    private String clientId;

    @Column(name = "CLIENT_SECRET")
    private String clientSecret;

    @Column(name = "SECRET_REQUIRED", nullable = false)
    private boolean secretRequired;

    @Column(name = "SCOPED", nullable = false)
    private boolean isScoped;

    @Column(name = "SCOPE")
    @ElementCollection
    private Set<String> scopes = new HashSet<>();

    @Column(name = "REDIRECT_URL")
    private String redirectURL;

    @Column(name = "GRANT_TYPES")
    @ElementCollection
    private Set<String> grantTypes = new HashSet<>();

    @Column(name = "AUTHORITIES")
    @OneToMany(targetEntity=Authority.class, fetch=FetchType.EAGER)
    private Collection<GrantedAuthority> authorities = new HashSet<>();

    @Column(name = "ACCESS_TOKEN_VALIDITY", nullable = false)
    private int tokenValidity;

    @Column(name = "REFRESH_TOKEN_VALIDITY", nullable = false)
    private int refreshTokenValidity;

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public Set<String> getResourceIds() {
        return Collections.singleton(RESOURCE_ID);
    }

    @Override
    public boolean isSecretRequired() {
        return secretRequired;
    }

    @Override
    public String getClientSecret() {
        return isSecretRequired() ? clientSecret : "";
    }

    @Override
    public boolean isScoped() {
        return isScoped;
    }

    @Override
    public Set<String> getScope() {
        return isScoped() ? scopes : new HashSet<>();
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return grantTypes;
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return Collections.singleton(redirectURL);
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return tokenValidity;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenValidity;
    }

    @Override
    public boolean isAutoApprove(String s) {
        return false;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return null;
    }
}
