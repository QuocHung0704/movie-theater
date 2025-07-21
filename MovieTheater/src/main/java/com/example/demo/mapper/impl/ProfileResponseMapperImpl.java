package com.example.demo.mapper.impl;

import com.example.demo.entity.Account;
import com.example.demo.entity.Member;
import com.example.demo.entity.Tier;
import com.example.demo.entity.response.ProfileResponse;
import com.example.demo.mapper.ProfileMapper;
import com.example.demo.service.TierRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ProfileResponseMapperImpl implements ProfileMapper {
    private final TierRepository tierRepository;

    public ProfileResponseMapperImpl(TierRepository tierRepository) {
        this.tierRepository = tierRepository;
    }

    @Override
    public ProfileResponse toProfileResponse(Account account, Member member) {
//        // Calculate total tickets from invoices
//        Integer totalTickets = invoiceRepository.countTicketInvoicesByAccountId(account.getAccountId());
//
//        // Add tickets from temporary accounts (phone/email matching)
//        if (account.getPhoneNumber() != null) {
//            totalTickets += invoiceRepository.countByCustomerPhone(account.getPhoneNumber());
//        }
//        if (account.getEmail() != null) {
//            totalTickets += invoiceRepository.countByCustomerEmail(account.getEmail());
//        }

        String tierName = null;
        Integer tierDiscountPercent = null;
        String tierDescription = null;
        String nextMembershipLevel = null;
        Long amountToNextLevel = null;
        if (member != null) {
            Long tierPoints = member.getTierPoints();
            List<Tier> tiers = tierRepository.findAll();
            tiers.sort(Comparator.comparingInt(Tier::getMinPoint));
            Tier currentTier = null;
            Tier nextTier = null;
            for (Tier tier : tiers) {
                if (tierPoints >= tier.getMinPoint()) {
                    currentTier = tier;
                } else if (nextTier == null) {
                    nextTier = tier;
                }
            }
            if (currentTier != null) {
                tierName = currentTier.getName();
                tierDiscountPercent = currentTier.getDiscountPercent();
                tierDescription = currentTier.getDescription();
            }
            if (nextTier == null) {
                nextMembershipLevel = nextTier.getName();
                amountToNextLevel = (long) (nextTier.getMinPoint() - tierPoints);
            }
        }
        return ProfileResponse.builder()
                .accountId(account.getAccountId())
                .fullName(account.getFullName())
                .username(account.getUsername())
                .email(account.getEmail())
                .phoneNumber(account.getPhoneNumber())
                .dateOfBirth(account.getDateOfBirth())
                .identityCard(account.getIdentityCard())
                .address(account.getAddress())
                .accountRole(account.getAccountRole().toString())
                .emailVerified(account.isEmailVerified())
                .avatar(account.getAvatar())
                .joinDate(account.getDateOfBirth())
                .memberId(member != null ? member.getMemberId() : null)
                .addScore(member != null && member.getAddScore() != null ? member.getAddScore() : 0L)
                .useScore(member != null && member.getUseScore() != null ? member.getUseScore() : 0L)
                .tierScore(member != null ? member.getTierPoints() : 0L)
                .exchangeScore(member != null ? member.getAvailableExchangePoints() : 0L)
//                .totalBookings(totalTickets)
                .totalSpent(member != null && member.getTotalSpent() != null ? member.getTotalSpent() : 0L)
                .availablePoints(member != null ? member.getAvailablePoints() : 0L)
                .pointsToNextTier(amountToNextLevel)
                .tierName(tierName)
                .tierDiscountPercent(tierDiscountPercent)
                .tierDescription(tierDescription)
                .nextMembershipLevel(nextMembershipLevel)
                .amountToNextLevel(amountToNextLevel)
                .build();
    }
}
