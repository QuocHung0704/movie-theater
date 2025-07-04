package com.example.demo.enums;

public enum MembershipLevel {
    BRONZE("Bronze", 0, 999999),
    SILVER("Silver", 1000000, 4999999),
    GOLD("Gold", 5000000, Long.MAX_VALUE);
    ;

    private String displayName;
    private final long minSpent;
    private final long maxSpent;

    MembershipLevel( String displayName, long minSpent, long maxSpent) {
        this.displayName = displayName;
        this.minSpent = minSpent;
        this.maxSpent = maxSpent;
    }

    public String getDisplayName() {
        return displayName;
    }

    public long getMinSpent() {
        return minSpent;
    }

    public long getMaxSpent() {
        return maxSpent;
    }

    public static MembershipLevel getMembershipLevel(long totalSpent) {
        if (totalSpent >= GOLD.minSpent) {
            return GOLD;
        } else if (totalSpent >= SILVER.minSpent) {
            return SILVER;
        } else {
            return BRONZE;
        }
    }

    public MembershipLevel getNextLevel() {
        switch (this) {
            case BRONZE:
                return SILVER;
            case SILVER:
                return GOLD;
            case GOLD:
            default:
                return null;
        }
    }

    public long getAmountToNextLevel(long currentSpent) {
        MembershipLevel nextLevel = getNextLevel();
        if (nextLevel == null) {
            return 0;
        }
        return Math.max(0, nextLevel.minSpent - currentSpent);
    }
}
