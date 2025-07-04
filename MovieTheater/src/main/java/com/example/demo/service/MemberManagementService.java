package com.example.demo.service;

import com.example.demo.entity.request.MemberFilterRequest;
import com.example.demo.entity.request.MemberRequest;
import com.example.demo.entity.response.MemberResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberManagementService {
    MemberResponse createMember(MemberRequest memberRequest);

    Page<MemberResponse> getMembersPage(MemberFilterRequest memberFilterRequest);
}
