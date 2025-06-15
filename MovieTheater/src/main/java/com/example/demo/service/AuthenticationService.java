package com.example.demo.service;

import com.example.demo.entity.Member;
import com.example.demo.entity.request.LoginRequest;
import com.example.demo.entity.request.RegisterRequest;
import com.example.demo.entity.response.LoginResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    Member register(RegisterRequest registerRequest);

    LoginResponse login(LoginRequest loginRequest);
}
