package com.example.demo.utils;

import com.example.demo.entity.Account;

public class ValidationUtils {
    public static void ensureAccountActive(Account account) {
        if (!account.isStatus()) {
            throw new IllegalArgumentException("Tài khoản đã bị khóa");
        }
    }

    public static boolean isNotBlank(String str) {
        return str != null && !str.trim().isEmpty();
    }

    public static boolean isFilterSpecified(String filter) {
        return filter != null && !filter.equalsIgnoreCase("all");
    }

    public static boolean matchesSearch(Account account, String keyword) {
        return (account.getFullName() != null && account.getFullName().toLowerCase().contains(keyword)) ||
                (account.getEmail() != null && account.getEmail().toLowerCase().contains(keyword)) ||
                (account.getPhoneNumber() != null && account.getPhoneNumber().toLowerCase().contains(keyword)) ||
                (account.getUsername() != null && account.getUsername().toLowerCase().contains(keyword));
    }
}
