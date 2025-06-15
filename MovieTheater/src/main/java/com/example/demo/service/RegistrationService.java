package com.example.demo.service;

import com.example.demo.entity.Member;
import com.example.demo.entity.request.RegisterRequest;

public interface RegistrationService {
    Member register(RegisterRequest request);
}
