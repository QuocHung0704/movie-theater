package com.example.demo.utils;

import com.example.demo.entity.Account;
import com.example.demo.repository.AccountRepository;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AccountUtils implements ApplicationContextAware {
    private static AccountRepository accountRepository;

    public Account getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return accountRepository.findByUsername(username).orElseThrow();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        accountRepository = applicationContext.getBean(AccountRepository.class);
    }
}
