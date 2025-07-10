package com.example.demo.entity.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResponse {
    // Account information
    private Long id;
    private Long accountId;
    private String fullName;
    private String username;
    private String email;
    private String phone;
    private String address;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate joinDate;

    private Boolean status;
    private Boolean emailVerified;
    private String avatar;

    // Member specific information
    private Long memberId;
    private Long addScore;
    private Long useScore;
    private Long availablePoints;
    private Long totalSpent;
    private String membershipLevel;
    private String nextMembershipLevel;
    private Long amountToNextLevel;
    private Integer totalBookings;
    private String lastActivity;
    private Long points;
}
