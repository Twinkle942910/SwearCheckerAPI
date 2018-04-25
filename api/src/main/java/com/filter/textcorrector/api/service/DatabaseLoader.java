package com.filter.textcorrector.api.service;

import com.filter.textcorrector.api.model.Authority;
import com.filter.textcorrector.api.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class DatabaseLoader {

    @Autowired
    private AuthorityRepository authorityRepository;

    @PostConstruct
    private void initDatabase() {
        Authority authorityAdmin = new Authority(1L, "ADMIN");
        Authority authorityUser = new Authority(2L, "USER");

        authorityRepository.save(authorityAdmin);
        authorityRepository.save(authorityUser);
    }
}