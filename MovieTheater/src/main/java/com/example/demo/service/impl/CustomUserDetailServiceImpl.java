package com.example.demo.service.impl;

import com.example.demo.service.CustomUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailServiceImpl implements CustomUserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        throw new UsernameNotFoundException(identifier);
    }
}
