package com.filter.textcorrector.api.service;

import com.filter.textcorrector.api.model.User;
import com.filter.textcorrector.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       /* User user = userRepository.findByUsername(username);

        if (user != null) {
            return user;
        }

        throw new UsernameNotFoundException(username);*/
        User user = userRepository.findByUsername(username);
        if(user != null){
            return org.springframework.security.core.userdetails.User.withUserDetails(user).build();
        }
        else {
            return null;
        }
    }

    public void save(User user) {
        userRepository.save(user);
    }
}
