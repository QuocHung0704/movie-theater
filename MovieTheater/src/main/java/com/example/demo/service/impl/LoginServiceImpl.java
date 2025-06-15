package com.example.demo.service.impl;

import com.example.demo.entity.Account;
import com.example.demo.entity.request.LoginRequest;
import com.example.demo.entity.response.LoginResponse;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.LoginService;
import com.example.demo.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final MemberRepository memberRepository;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        if (loginRequest == null || loginRequest.getIdentifier() == null || loginRequest.getPassword() == null) {
            throw new IllegalArgumentException("Thiếu thông tin đăng nhập");
        }

        Account account = accountRepository.findByUsernameOrEmailOrPhoneNumber(loginRequest.getIdentifier())
                .orElseThrow(() -> new UsernameNotFoundException("Tài khoản không tồn tại"));

        if (!account.isStatus()) throw new IllegalArgumentException("Tài khoản đã bị khóa");
        if (!passwordEncoder.matches(loginRequest.getPassword(), account.getPassword())) throw new IllegalArgumentException("Sai mật khầu");
//        if (!account.isEmailVerified()) throw new IllegalIdentifierException("Vui lòng xác minh email");

        String token = tokenService.generateToken(account);

        return LoginResponse.builder()
                .accountId(account.getAccountId())
                .email(account.getEmail())
                .fullName(account.getFullName())
                .phoneNumber(account.getPhoneNumber())
                .accountRole(account.getAccountRole())
                .status(account.isStatus())
                .token(token)
                .build();
    }

    @Override
    public LoginResponse loginGoogle(LoginRequest loginRequest) {
        return null;
    }
}
