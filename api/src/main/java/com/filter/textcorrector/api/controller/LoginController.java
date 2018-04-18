package com.filter.textcorrector.api.controller;

import com.filter.textcorrector.api.model.Authority;
import com.filter.textcorrector.api.model.Client;
import com.filter.textcorrector.api.model.User;
import com.filter.textcorrector.api.service.ClientService;
import com.filter.textcorrector.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class LoginController {
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

    @Value("${security.jwt.access-token-validity}")
    private int accessTokenValidity;

    @Value("${security.jwt.refresh-token-validity}")
    private int refreshTokenValidity;

    @RequestMapping(value={"/", "/home"}, method = RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = userService.loadUserByUsername(auth.getName());
        ClientDetails client = clientDetailsService.loadClientByClientId(user.getUsername() + "id");

        modelAndView.addObject("client_id", client.getClientId());
        modelAndView.addObject("client_secret", user.getPassword() + "secret");
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @RequestMapping(value={"/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        UserDetails userExists = userService.loadUserByUsername(user.getUsername());
        if (userExists != null) {
            bindingResult
                    .rejectValue("username", "error.user",
                            "There is already a user registered with the username provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {

            user.setEnabled(true);
            user.setPassword(userPasswordEncoder.encode(user.getPassword()));
            user.getAuthorities().add(new Authority(2L, "USER"));

            ((UserService)userService).save(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registration");

            String clientId = user.getUsername() + "id";
            String clientSecret = user.getPassword() + "secret";

            ClientDetails client = clientDetailsService.loadClientByClientId(clientId);
            if(client == null){
                Client clientSimple = new Client();
                clientSimple.setClientId(clientId);
                clientSimple.setClientSecret(oauthClientPasswordEncoder.encode(clientSecret));
                clientSimple.setScope("read,write");
                clientSimple.setAccessTokenValidity(accessTokenValidity);
                clientSimple.setRefreshTokenValidity((long) refreshTokenValidity);
                clientSimple.setAuthorities("USER_CLIENT");
                clientSimple.setResourceIds("swearchecker-rest-api");
                clientSimple.setGrantTypes("client_credentials,password,refresh_token");

                ((ClientService)clientDetailsService).save(clientSimple);
            }

        }
        return modelAndView;
    }

    @RequestMapping(value="/admin/home", method = RequestMethod.GET)
    public ModelAndView adminHome(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = userService.loadUserByUsername(auth.getName());
        modelAndView.addObject("userName", "Welcome " + user.getUsername() + " " + user.getAuthorities());
        modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Authority");
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }

}
