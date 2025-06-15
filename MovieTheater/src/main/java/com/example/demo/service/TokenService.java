package com.example.demo.service;

import com.example.demo.entity.Account;
import org.springframework.context.annotation.Bean;

public interface TokenService {
    String generateToken(Account account);

    Account getAccountByToken(String token);
}
