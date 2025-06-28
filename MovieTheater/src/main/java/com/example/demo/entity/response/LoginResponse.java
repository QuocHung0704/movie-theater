package com.example.demo.entity.response;

import com.example.demo.enums.UserRoleEnums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    private Long accountId;
    private Long memberId;
    private String email;
    private String fullName;
    private String phoneNumber;
    private String avatar;
    private UserRoleEnums accountRole;
    private boolean status;
    private String token;
}
