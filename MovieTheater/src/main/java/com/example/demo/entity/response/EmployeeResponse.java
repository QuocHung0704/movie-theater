package com.example.demo.entity.response;

import com.example.demo.enums.UserRoleEnums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {
    private Long employeeId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private UserRoleEnums accountRole;
    private Boolean status;
    private String username;
    private String avatar;
    private LocalDate dateOfBirth;
}
