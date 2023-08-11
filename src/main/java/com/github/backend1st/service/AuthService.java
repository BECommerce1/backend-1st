package com.github.backend1st.service;

import com.github.backend1st.web.dto.LoginRequest;
import com.github.backend1st.web.dto.RegisterRequest;
import org.springframework.transaction.annotation.Transactional;

@Transactional(transactionManager = "tmJpa")
public class AuthService {
    public boolean signUp(RegisterRequest registerRequest) {
        // TODO

        return false;
    }

    public String login(LoginRequest loginRequest) {
        // TODO


        return "";

    }
}
