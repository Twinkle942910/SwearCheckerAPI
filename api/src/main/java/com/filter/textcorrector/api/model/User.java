package com.filter.textcorrector.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Table(name = "USER_", uniqueConstraints = {@UniqueConstraint(columnNames = {"USER_NAME"})})
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class User implements UserDetails, Serializable {

    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @Column(name = "USER_NAME", nullable = false)
    @NotEmpty(message = "*Please provide the username")
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    @Length(min = 8, message = "*Your password must have at least 8 characters")
    private String password;

    @Column(name = "ACCOUNT_EXPIRED")
    private boolean accountExpired;

    @Column(name = "ACCOUNT_LOCKED")
    private boolean accountLocked;

    @Column(name = "CREDENTIALS_EXPIRED")
    private boolean credentialsExpired;

    @Column(name = "ENABLED", nullable = false)
    private boolean enabled;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "USERS_AUTHORITIES",
            joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID"))
    @OrderBy
    @JsonIgnore
    private Collection<Authority> authorities = new HashSet<>();

    @Override
    public boolean isAccountNonExpired() {
        return !isAccountExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isAccountLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !isCredentialsExpired();
    }
}
