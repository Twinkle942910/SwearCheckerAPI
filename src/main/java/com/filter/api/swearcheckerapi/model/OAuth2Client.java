package com.filter.api.swearcheckerapi.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "oauth_client_details")
@Data
public class OAuth2Client {
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
}