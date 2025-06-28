package com.example.demo.utils;

import com.example.demo.entity.Account;

public class ValidationUtils {
    public static void ensureAccountActive(Account account) {
        if (!account.isStatus()) {
            throw new IllegalArgumentException("Tài khoản đã bị khóa");
        }
    }
}
