package com.example.demo.entity.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileResponse {
    private Long accountId;
    private String fullName;
    private String username;
    private String email;
    private String phoneNumber;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;

    private String identityCard;
    private String address;
    private String accountRole;
    private Boolean emailVerified;
    private String avatar;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate joinDate;

    private Long memberId;
    private Long addScore;
    private Long useScore;
    private Long tierScore;
    private Long exchangeScore;

    private Integer totalBookings;
    private Long totalSpent;
    private Long availablePoints;
    private Long pointsToNextTier;

    private String tierName;
    private Integer tierDiscountPercent;
    private String tierDescription;
    private String nextMembershipLevel;
    private Long amountToNextLevel;
}
