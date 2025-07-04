package com.example.demo.service.impl;

import com.example.demo.entity.Account;
import com.example.demo.entity.Member;
import com.example.demo.entity.request.MemberRequest;
import com.example.demo.entity.response.MemberResponse;
import com.example.demo.enums.UserRoleEnums;
import com.example.demo.mapper.MemberMapper;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.MemberManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberManagementImpl implements MemberManagementService {

    private final PasswordEncoder passwordEncoder;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberMapper memberMapper;

    public MemberManagementImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public MemberResponse createMember(MemberRequest memberRequest) {
        validateMember(memberRequest);
        Account account = Account.builder()
                .fullName(memberRequest.getFullName())
                .username(memberRequest.getUsername())
                .email(memberRequest.getEmail())
                .phoneNumber(String.valueOf(memberRequest.getPhoneNumber()))
                .password(passwordEncoder.encode(memberRequest.getPassword()))
                .dateOfBirth(memberRequest.getDateOfBirth())
                .identityCard(memberRequest.getIdentityCard())
                .address(memberRequest.getAddress())
                .accountRole(UserRoleEnums.MEMBER)
                .status(true)
                .emailVerified(false)
                .build();
        Account savedAccount = accountRepository.save(account);

        Member member = Member.builder()
                .account(savedAccount)
                .addScore(memberRequest.getInitialPoints() != null ? memberRequest.getInitialPoints() : 0L)
                .useScore(0L)
                .totalSpent(memberRequest.getInitialSpent() != null ? memberRequest.getInitialSpent() : 0L)
                .build();
        memberRepository.save(member);
        return memberMapper.toMemberResponse(savedAccount);
    }

    private void validateMember(MemberRequest memberRequest) {
        if (accountRepository.existsByUsername(memberRequest.getUsername())) {
            throw new RuntimeException("Tên đăng nhập đã tồn tại");
        }
        if (accountRepository.existsByEmail(memberRequest.getEmail())) {
            throw new RuntimeException("Email đã được sử dụng");
        }
        if (accountRepository.existsByPhoneNumber(memberRequest.getPhoneNumber())) {
            throw new RuntimeException("Số điện thoại đã được sử dụng");
        }
        if (memberRequest.getIdentityCard() != null &&
                accountRepository.existsByIdentityCard(memberRequest.getIdentityCard())) {
            throw new RuntimeException("Số CMND/CCCD đã được sử dụng");
        }
    }
}
