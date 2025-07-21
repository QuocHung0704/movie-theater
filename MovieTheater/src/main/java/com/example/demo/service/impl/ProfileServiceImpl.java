package com.example.demo.service.impl;

import com.example.demo.entity.Account;
import com.example.demo.entity.Member;
import com.example.demo.entity.response.ProfileResponse;
import com.example.demo.mapper.impl.ProfileResponseMapperImpl;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final AccountRepository accountRepository;
    private final MemberRepository memberRepository;
    private final ProfileResponseMapperImpl profileResponseMapperImpl;

    @Override
    public ProfileResponse getUserProfileById(Long userId) {
        Account account = accountRepository.findByAccountId(userId);
        if (account == null) {
            throw new RuntimeException("Không tìm thấy tài khoản");
        }

        Member member = memberRepository.findByAccount_AccountId(account.getAccountId());
        if (member != null) {
            member.initializeScoreFields();
            memberRepository.save(member);
        }
        return profileResponseMapperImpl.toProfileResponse(account, member);
    }
}
