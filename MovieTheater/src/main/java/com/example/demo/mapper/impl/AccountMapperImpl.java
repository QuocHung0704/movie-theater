package com.example.demo.mapper.impl;

import com.example.demo.entity.Account;
import com.example.demo.entity.request.MemberRequest;
import com.example.demo.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountMapperImpl implements AccountMapper {
    private final PasswordEncoder passwordEncoder;

    @Override
    public void updateAccount(Account account, MemberRequest memberRequest) {
        account.setFullName(memberRequest.getFullName());
        if (memberRequest.getUsername() != null && !memberRequest.getUsername().trim().isEmpty()) {
            account.setUsername(memberRequest.getUsername());
        }
        account.setEmail(memberRequest.getEmail());
        account.setPhoneNumber(memberRequest.getPhoneNumber());
        account.setDateOfBirth(memberRequest.getDateOfBirth());
        account.setIdentityCard(memberRequest.getIdentityCard());
        account.setAddress(memberRequest.getAddress());

        if (memberRequest.getAccountRole() != null) {
            account.setAccountRole(memberRequest.getAccountRole());
        }

        if (memberRequest.getPassword() != null && !memberRequest.getPassword().trim().isEmpty()) {
            account.setPassword(passwordEncoder.encode(memberRequest.getPassword()));
        }

        if (memberRequest.getStatus() != null) {
            account.setStatus(memberRequest.getStatus());
        }
    }
}
