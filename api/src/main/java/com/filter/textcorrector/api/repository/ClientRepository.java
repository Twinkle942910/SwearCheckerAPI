package com.filter.textcorrector.api.repository;

import com.filter.textcorrector.api.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByClientId(String clientId);
}
