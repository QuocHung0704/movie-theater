package com.example.demo.enums;

public enum FilmVersion {
    STANDARD_2D("2D"),
    IMAX_2D("IMAX 2D"),
    STANDARD_3D("3D"),
    IMAX_3D("IMAX 3D"),
    VIP_2D("VIP 2D"),
    VIP_3D("VIP 3D"),
    FOUR_DX("4DX"),
    DOLBY_ATMOS("Dolby Atmos"),
    SCREEN_X("Screen X");

    private final String displayName;

    FilmVersion(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
