package com.example.demo.mapper.impl;

import com.example.demo.entity.Account;
import com.example.demo.entity.Member;
import com.example.demo.entity.response.MemberResponse;
import com.example.demo.mapper.MemberMapper;
import com.example.demo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberMapperImpl implements MemberMapper {
    @Autowired
    private MemberRepository memberRepository;

    @Override
    public MemberResponse toMemberResponse(Account account) {
        Member member = memberRepository.findByAccount_AccountId(account.getAccountId());

        return MemberResponse.builder()
                .id(account.getAccountId())
                .accountId(account.getAccountId())
                .fullName(account.getFullName())
                .username(account.getUsername())
                .email(account.getEmail())
                .phone(account.getPhoneNumber())
                .address(account.getAddress())
                .dateOfBirth(account.getDateOfBirth())
                .joinDate(account.getDateOfBirth())
                .status(account.isStatus())
                .emailVerified(account.isEmailVerified())
                .avatar(account.getAvatar())
                .memberId(member != null ? member.getMemberId() : null)
                .addScore(member != null && member.getAddScore() != null ? member.getAddScore() : 0L)
                .useScore(member != null && member.getUseScore() != null ? member.getUseScore() : 0L)
                .availablePoints(member != null ? member.getAvailablePoints() : 0L)
                .totalSpent(member != null && member.getTotalSpent() != null ? member.getTotalSpent() : 0L)
                .membershipLevel(member != null ? member.getMembershipLevel().getDisplayName() : "Bronze")
                .nextMembershipLevel(member != null && member.getNextMembershipLevel() != null ?
                        member.getNextMembershipLevel().getDisplayName() : null)
//                .amountToNextLevel(member != null ? member.getAmountToNextLevel() : 0L)
//                .totalBookings(invoiceRepository.countByAccountId(account.getAccountId()))
                .lastActivity("")
                .points(member != null ? member.getAvailablePoints() : 0L)
                .build();
    }
}
