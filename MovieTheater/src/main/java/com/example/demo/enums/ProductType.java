package com.example.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.checkerframework.checker.units.qual.A;

@AllArgsConstructor
public enum ProductType {
    MOVIE_TICKET("Vé phim"),
    COMBO("Combo thức ăn"),
    DRINK("Đồ uống"),
    SNACK("Đồ ăn vặt"),
    FOOD("Thức ăn"),
    OTHER("Khác");


    private final String displayName;

    public String getDisplayName() {
        return displayName;
    }
}
