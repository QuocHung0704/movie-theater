package com.example.demo.service;

import com.example.demo.entity.request.LoginRequest;
import com.example.demo.entity.response.LoginResponse;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {
    LoginResponse login(LoginRequest loginRequest);

    LoginResponse loginGoogle(LoginRequest loginRequest);
}
