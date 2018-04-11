package com.filter.api.swearcheckerapi.controller;

import com.filter.api.swearcheckerapi.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/infos")
public class InfoController {

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "user", method = RequestMethod.GET)
    public User helloUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return user;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "admin", method = RequestMethod.GET)
    public String helloAdmin() {
        return "hello admin";
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @RequestMapping(value = "client", method = RequestMethod.GET)
    public String helloClient() {
        return "hello user authenticated by normal client";
    }

   // @PreAuthorize("hasRole('ROLE_TRUSTED_CLIENT')")
    @PreAuthorize("hasAuthority('ROLE_TRUSTED_CLIENT')")
    @RequestMapping(value = "trusted_client", method = RequestMethod.GET)
    public String helloTrustedClient() {
        System.out.println("here");
        return "hello user authenticated by trusted client";
    }

    @RequestMapping(value = "principal", method = RequestMethod.GET)
    public Object getPrincipal() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal;
    }

    @RequestMapping(value = "roles", method = RequestMethod.GET)
    public Object getRoles() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }

    @RequestMapping(value = "hello", method = RequestMethod.GET)
   // @PreAuthorize("hasRole('ROLE_USER')")
    @PreAuthorize("hasAuthority('USER')")
    public Object hello() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "heello";
    }
}
