package com.example.demo.service;

import com.example.demo.entity.response.ProfileResponse;

public interface ProfileService {
    ProfileResponse getUserProfileById(Long userId);
}
