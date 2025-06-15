//package com.example.demo.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class VerificationToken {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false, unique = true)
//    private String token;
//
//    @Column(nullable = false)
//    private LocalDateTime expiryTime;
//
//    @OneToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "account_id")
//    private Account account;
//
//    public VerificationToken( String token,  Account account, LocalDateTime expiryTime) {
//        this.id = id;
//        this.token = token;
//        this.expiryTime = expiryTime;
//        this.account = account;
//    }
//}
