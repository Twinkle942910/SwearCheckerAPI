package com.filter.api.swearcheckerapi.service;

import com.filter.api.swearcheckerapi.model.Client;
import com.filter.api.swearcheckerapi.model.User;
import com.filter.api.swearcheckerapi.repository.ClientRepository;
import com.filter.api.swearcheckerapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("clientDetailsService")
public class ClientService implements ClientDetailsService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    @Transactional(readOnly = true)
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        return clientRepository.findByClientId(clientId);
    }

    public void save(Client client) {
        clientRepository.save(client);
    }
}
