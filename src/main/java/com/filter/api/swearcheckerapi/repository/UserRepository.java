package com.filter.api.swearcheckerapi.repository;

import com.filter.api.swearcheckerapi.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findOneByUsername(String username);
}