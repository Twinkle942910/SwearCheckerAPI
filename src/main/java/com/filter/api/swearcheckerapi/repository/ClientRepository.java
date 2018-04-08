package com.filter.api.swearcheckerapi.repository;

import com.filter.api.swearcheckerapi.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByClientId(String clientId);
}
