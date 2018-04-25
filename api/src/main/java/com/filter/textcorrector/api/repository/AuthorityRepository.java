package com.filter.textcorrector.api.repository;

import com.filter.textcorrector.api.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
