package com.example.demo.controller;

import com.example.demo.entity.Member;
import com.example.demo.entity.request.LoginGoogleRequest;
import com.example.demo.entity.request.LoginRequest;
import com.example.demo.entity.request.RegisterRequest;
import com.example.demo.entity.response.LoginGoogleResponse;
import com.example.demo.entity.response.LoginResponse;
import com.example.demo.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@CrossOrigin("*")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("register")
    public ResponseEntity<Member> register(@Valid @RequestBody RegisterRequest registerRequest) {
        Member savedMember = authenticationService.register(registerRequest);
        return ResponseEntity.ok(savedMember);
    }

    @PostMapping("login")
    public ResponseEntity login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = authenticationService.login(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("login/google")
    public ResponseEntity loginGoogle(@Valid @RequestBody LoginGoogleRequest loginGoogleRequest) {
        LoginResponse loginResponse = authenticationService.loginGoole(loginGoogleRequest);
        LoginGoogleResponse loginGoogleResponse = LoginGoogleResponse.builder()
                .token(loginResponse.getToken())
                .user(loginResponse)
                .build();
        return ResponseEntity.ok(loginGoogleResponse);
    }
}
