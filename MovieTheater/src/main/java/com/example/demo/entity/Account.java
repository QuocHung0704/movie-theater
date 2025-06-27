package com.example.demo.entity;

import com.example.demo.enums.UserRoleEnums;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Table(name = "account")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;
    private String address;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dateOfBirth;
    private String email;
    private String fullName;
    private String identityCard;
    private String password;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private UserRoleEnums accountRole;
    private boolean status;
    @Column(unique = true)
    private String username;
    @Column(nullable = false)
    private boolean emailVerified = false;
    private String avatar;
}
