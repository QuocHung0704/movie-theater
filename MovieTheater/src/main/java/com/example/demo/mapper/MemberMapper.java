package com.example.demo.mapper;

import com.example.demo.entity.Account;
import com.example.demo.entity.Member;
import com.example.demo.entity.request.MemberRequest;
import com.example.demo.entity.response.MemberResponse;

public interface MemberMapper {
    MemberResponse toMemberResponse(Account account);
    void memberUpdater(Member member, MemberRequest memberRequest);
}
