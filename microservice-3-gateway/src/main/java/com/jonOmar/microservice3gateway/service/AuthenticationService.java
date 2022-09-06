package com.jonOmar.microservice3gateway.service;

import com.jonOmar.microservice3gateway.model.User;

public interface AuthenticationService {
    User signInAndReturnJWT(User signInrequest);
}
