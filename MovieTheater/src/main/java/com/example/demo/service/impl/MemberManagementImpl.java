package com.example.demo.service.impl;

import com.example.demo.entity.Account;
import com.example.demo.entity.Member;
import com.example.demo.entity.request.MemberFilterRequest;
import com.example.demo.entity.request.MemberRequest;
import com.example.demo.entity.response.MemberResponse;
import com.example.demo.enums.UserRoleEnums;
import com.example.demo.mapper.AccountMapper;
import com.example.demo.mapper.MemberMapper;
import com.example.demo.mapper.impl.MemberMapperImpl;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.MemberManagementService;
import com.example.demo.utils.ValidationUtils;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.utils.ValidationUtils.isFilterSpecified;
import static com.example.demo.utils.ValidationUtils.matchesSearch;

@Service
public class MemberManagementImpl implements MemberManagementService {

    private final PasswordEncoder passwordEncoder;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private AccountMapper accountMapper;

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

    @Override
    public Page<MemberResponse> getMembersPage(MemberFilterRequest memberFilterRequest) {
        Pageable pageable = memberFilterRequest.getPageable();
        String searchTerm = memberFilterRequest.getSearch();
        String membershipFilter = memberFilterRequest.getMembershipFilter();
        String status = memberFilterRequest.getStatusFilter();

        List<Account> memberAccounts = accountRepository.findByAccountRole(UserRoleEnums.MEMBER);

        if (ValidationUtils.isNotBlank(searchTerm)) {
            String lowerSearch = searchTerm.toLowerCase();
            memberAccounts = memberAccounts.stream()
                    .filter(account -> matchesSearch(account, lowerSearch))
                    .collect(Collectors.toList());
        }

        List<MemberResponse> memberResponses = memberAccounts.stream()
                .map(memberMapper::toMemberResponse)
                .collect(Collectors.toList());

        if (isFilterSpecified(membershipFilter)) {
            memberResponses = memberResponses.stream()
                    .filter(member -> applyMembershipFilter(member, membershipFilter))
                    .collect(Collectors.toList());
        }

        if (isFilterSpecified(status)) {
            memberResponses = memberResponses.stream()
                    .filter(member -> applyStatusFilter(member, status))
                    .collect(Collectors.toList());
        }

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), memberResponses.size());
        List<MemberResponse> pageContent = start < end
                ? memberResponses.subList(start, end)
                : Collections.emptyList();

        return new PageImpl<>(pageContent, pageable, memberResponses.size());

    }

    @Override
    public MemberResponse getMemberById(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thành viên"));
        return memberMapper.toMemberResponse(member.getAccount());
    }

    @Override
    public MemberResponse updateMember(Long memberId, MemberRequest memberRequest) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thành viên"));
        Account account = member.getAccount();
        validateMember(memberRequest);

        accountMapper.updateAccount(account, memberRequest);
        memberMapper.memberUpdater(member, memberRequest);
        accountRepository.save(account);
        memberRepository.save(member);
        return memberMapper.toMemberResponse(account);
    }

    @Override
    @Transactional
    public String deactiveMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thành viên"));

        Account account = member.getAccount();
        if (!account.isStatus()) {
            throw new RuntimeException("Tài khoản đã bị vô hiệu hóa");
        }
        account.setStatus(false);
        accountRepository.save(account);
        return "Tài khoản thành viên đã bị vô hiệu hóa";
    }

    @Override
    public String activeMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thành viên"));

        Account account = member.getAccount();
        account.setStatus(true);
        accountRepository.save(account);
        return "Tài khoản thành viên đã được kích hoạt";
    }

    @Override
    public String deleteMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thành viên"));
        Account account = member.getAccount();
        account.setStatus(false);
        accountRepository.save(account);
        return "Thành viên đã được xóa";

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

    private boolean applyMembershipFilter(MemberResponse member, String membershipFilter) {
        if ("all".equals(membershipFilter)) {
            return true;
        }

        if (member.getMembershipLevel() == null) {
            return "bronze".equals(membershipFilter.toLowerCase());
        }

        return member.getMembershipLevel().toLowerCase().equals(membershipFilter.toLowerCase());
    }

    private boolean applyStatusFilter(MemberResponse member, String statusFilter) {
        switch (statusFilter.toLowerCase()) {
            case "active":
                return member.getStatus() != null && member.getStatus();
            case "inactive":
                return member.getStatus() == null || !member.getStatus();
            default:
                return true;
        }
    }
}
