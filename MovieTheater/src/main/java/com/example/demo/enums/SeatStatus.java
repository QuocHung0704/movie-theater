package com.example.demo.enums;

public enum SeatStatus {
    SEAT_CAN_CHOOSE("Có thể chọn"),
    SEAT_SOLD("Đã bán"),
    SEAT_HELD("Đang giữ"),
    SEAT_MAINTENANCE("Bảo trì");

    private final String displayName;

    SeatStatus(String displayName) {
        this.displayName = displayName;
    }
    public String getDisplayName() {
        return displayName;
    }
}
