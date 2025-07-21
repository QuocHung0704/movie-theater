package com.example.demo.mapper;

import com.example.demo.entity.Account;
import com.example.demo.entity.Member;
import com.example.demo.entity.response.ProfileResponse;
import org.springframework.context.annotation.Profile;

public interface ProfileMapper {
    ProfileResponse toProfileResponse(Account account, Member member);
}
