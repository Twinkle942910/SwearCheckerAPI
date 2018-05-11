package com.filter.textcorrector.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService userService;

    @Autowired
    @Qualifier("clientDetailsServiceImpl")
    private ClientDetailsService clientDetailsService;

    @Autowired
    private PasswordEncoder userPasswordEncoder;

    @Autowired
    private PasswordEncoder oauthClientPasswordEncoder;

    @RequestMapping(value={"/keys"}, method = RequestMethod.GET)
    public ModelAndView keys(){
        ModelAndView modelAndView = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = userService.loadUserByUsername(auth.getName());
        ClientDetails client = clientDetailsService.loadClientByClientId(user.getUsername() + "id");

        modelAndView.addObject("client_id", client.getClientId());
        modelAndView.addObject("client_secret", user.getPassword() + "secret");

        modelAndView.setViewName("keys");
        return modelAndView;
    }

}
