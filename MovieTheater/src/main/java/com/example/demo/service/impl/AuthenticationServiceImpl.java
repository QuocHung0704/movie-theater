package com.example.demo.service.impl;

import com.example.demo.entity.Member;
import com.example.demo.entity.request.LoginRequest;
import com.example.demo.entity.request.RegisterRequest;
import com.example.demo.entity.response.LoginResponse;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.LoginService;
import com.example.demo.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private RegistrationService registrationService;

    private final LoginService loginService;

    @Override
    public Member register(RegisterRequest registerRequest) {
        return registrationService.register(registerRequest);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        return loginService.login(loginRequest  );
    }
}
