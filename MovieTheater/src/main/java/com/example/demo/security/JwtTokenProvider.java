package com.example.demo.security;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

public class JwtTokenProvider {

    public static FirebaseToken verifyFirebaseToken(String token) {
        try {
            return FirebaseAuth.getInstance().verifyIdToken(token);
        } catch (FirebaseAuthException ex) {
            throw new IllegalArgumentException("Token không hợp lệ: " + ex.getMessage());
        }
    }

    public static String extractEmail(FirebaseToken token) {
        String email = token.getEmail();
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email không hợp lệ từ token Google");
        }
        return email;
    }

}
