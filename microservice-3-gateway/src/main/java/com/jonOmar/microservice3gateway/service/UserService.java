package com.jonOmar.microservice3gateway.service;

import com.jonOmar.microservice3gateway.model.Role;
import com.jonOmar.microservice3gateway.model.User;

import java.util.Optional;

public interface UserService {
    User saveUser(User user);

    Optional<User> findByUsername(String username);

    void changeRole(Role newRole, String username);
}
