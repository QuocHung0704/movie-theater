package com.example.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MovieStatus {
    COMING_SOON(0),
    NOW_SHOWING(1),
    ENDED(2);

    private int value;
}
