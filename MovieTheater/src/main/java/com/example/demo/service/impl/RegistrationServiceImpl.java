package com.example.demo.service.impl;

import com.example.demo.entity.Account;
import com.example.demo.entity.Member;
import com.example.demo.entity.request.RegisterRequest;
import com.example.demo.enums.UserRoleEnums;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
//    private final EmailService emailService;

    //check confirm password
    @Override
    public Member register(RegisterRequest request) {
        if (request == null || !request.getPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("Invalid password confirmation");
        }

        //create account and map
        Account account = Account.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .fullName(request.getFullName())
                .password(passwordEncoder.encode(request.getPassword()))
                .identityCard(request.getIdentityCard())
                .phoneNumber(request.getPhoneNumber())
                .accountRole(UserRoleEnums.MEMBER)
                .status(true)
                .dateOfBirth(request.getDateOfBirth())
                .build();

        //create member and set account for member
        Member member = new Member();
        member.setAccount(account);

        //save to database
        member = memberRepository.save(member);

//        //generate token
//        String token = UUID.randomUUID().toString();
//        //create verificationToken
//        VerificationToken verificationToken = new VerificationToken(token, account, LocalDateTime.now().plusDays(1));
//        verificationTokenRepository.save(verificationToken);

        //send email
//        String verifyUrl = "http://14.169.77.53:8080/api/auth/verify?token=" + token;
//        emailService.sendHtmlMail(request.getEmail(), "Xác nhận tài khoản Movie Theater", request.getFullName(), verifyUrl);

        return member;
    }
}
