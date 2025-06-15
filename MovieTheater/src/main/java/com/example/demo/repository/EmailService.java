package com.example.demo.repository;

public interface EmailService {
    void sendHtmlMail(String to, String subject, String fullName, String verifyUrl);

    /**
     * Send email with verification code for email change
     */
    void sendEmailChangeVerification(String to, String fullName, String verificationCode);
}