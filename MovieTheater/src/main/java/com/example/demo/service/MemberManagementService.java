package com.example.demo.service;

import com.example.demo.entity.request.MemberFilterRequest;
import com.example.demo.entity.request.MemberRequest;
import com.example.demo.entity.response.MemberResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberManagementService {
    MemberResponse createMember(MemberRequest memberRequest);
    Page<MemberResponse> getMembersPage(MemberFilterRequest memberFilterRequest);
    MemberResponse getMemberById(Long memberId);
    MemberResponse updateMember(Long memberId, MemberRequest memberRequest);
    String deactiveMember(Long memberId);
    String activeMember(Long memberId);
    String deleteMember(Long memberId);
    String addPointsToMember(Long memberId, Long points);
    String usePointsToMember(Long memberId, Long points);
    String addSpendingFromMember(Long memberId, Long amount);
    byte[] exportMemberToExcel();
}
