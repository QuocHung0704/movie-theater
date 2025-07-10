package com.example.demo.mapper;

import com.example.demo.entity.Account;
import com.example.demo.entity.request.MemberRequest;

public interface AccountMapper {
    void updateAccount(Account account, MemberRequest memberRequest);
}
