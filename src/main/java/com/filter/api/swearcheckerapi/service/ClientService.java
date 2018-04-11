package com.filter.api.swearcheckerapi.service;

import com.filter.api.swearcheckerapi.model.Client;
import com.filter.api.swearcheckerapi.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

@Service("clientDetailsServiceImpl")
public class ClientService implements ClientDetailsService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        ClientDetails clientDetails = clientRepository.findByClientId(clientId);

        if (clientDetails == null) {
            throw new UsernameNotFoundException(String.format("ClientDetails %s does not exist!", clientId));
        }

        return new BaseClientDetails(clientDetails);
    }

    public void save(Client client) {
        clientRepository.save(client);
    }
}
