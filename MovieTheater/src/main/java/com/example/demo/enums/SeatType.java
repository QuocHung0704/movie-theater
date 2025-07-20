package com.example.demo.enums;

public enum SeatType {
    NORMAL("", 1.0),
    VIP("Ghế VIP", 1.5),
    COUPLE("Ghế đôi", 2.0);

    private final String displayName;
    private final double priceMultilier;

    SeatType(String displayName, double priceMultilier) {
        this.displayName = displayName;
        this.priceMultilier = priceMultilier;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getPriceMultilier() {
        return priceMultilier;
    }
}
