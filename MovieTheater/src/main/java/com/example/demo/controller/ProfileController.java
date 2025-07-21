package com.example.demo.controller;

import com.example.demo.entity.Account;
import com.example.demo.entity.response.ProfileResponse;
import com.example.demo.service.ProfileService;
import com.example.demo.service.impl.ProfileServiceImpl;
import com.example.demo.utils.AccountUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/profile")
@CrossOrigin("*")
@SecurityRequirement(name = "api")
@RequiredArgsConstructor
public class ProfileController {

    @Autowired
    private AccountUtils accountUtils;

    private final ProfileService profileService;

    @GetMapping("")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ProfileResponse> getUserProfile() {
        try {
            Account currentUser = accountUtils.getCurrentUser();
            Long userId = currentUser.getAccountId();
            ProfileResponse profile = profileService.getUserProfileById(userId);
            return ResponseEntity.ok(profile);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }
}
