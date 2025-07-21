package com.example.demo.entity;

import com.example.demo.enums.MembershipLevel;
import com.google.common.xml.XmlEscapers;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(name = "add_score")
    private Long addScore;

    @Column(name = "use_score")
    private Long useScore;

    @Column(name = "total_spent")
    private Long totalSpent = 0L;

    @Column(name = "tier_score")
    private Long tierScore = 0L;

    @Column(name = "exchange_score")
    private Long exchangeScore = 0L;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Account account;

    @Transient
    public MembershipLevel getMembershipLevel() {
        return MembershipLevel.getMembershipLevel(this.totalSpent != null ? this.totalSpent : 0L);
    }

    @Transient
    public Long getAvailablePoints() {
        long add = this.addScore != null ? this.addScore : 0L;
        long use = this.useScore != null ? this.useScore : 0L;
        return add - use;
    }

    @Transient
    public Long getAvailableExchangePoints() {
        return this.exchangeScore != null ? this.exchangeScore : 0L;
    }

    @Transient
    public Long getTierPoints() {
        return this.tierScore != null ? this.tierScore : 0L;
    }

    public void addPoints(Long points) {
        if (points != null && points > 0) {
            this.addScore = (this.addScore != null ? this.addScore : 0L) + points;
            this.tierScore = (this.tierScore != null ? this.tierScore : 0L) + points;
            this.exchangeScore = (this.exchangeScore != null ? this.exchangeScore : 0L) + points;
        }
    }

    public boolean usePoints(Long points) {
        if (points != null && points > 0) {
            Long available = getAvailablePoints();
            Long availableExchange = getAvailableExchangePoints();

            if (available >= points && availableExchange >= points) {
                this.useScore = (this.useScore != null ? this.useScore : 0L) + points;
                this.exchangeScore = (this.exchangeScore != null ? this.exchangeScore : 0L) - points;
                return true;
            }
        }
        return false;
    }

    public void addSpending(Long amount) {
        if (amount != null && amount > 0) {
            this.totalSpent = (this.totalSpent != null ? this.totalSpent : 0L) + amount;
            // Add 1 point for every 10,000 VND spent
            Long pointsToAdd = amount / 10000;
            if (pointsToAdd > 0) {
                addPoints(pointsToAdd); // This will add to both tier and exchange scores
            }
        }
    }

    @Transient
    public MembershipLevel getTierBasedMembershipLevel() {
        Long tierPoints = getTierPoints();
        if (tierPoints < 100) {
            return MembershipLevel.BRONZE;
        } else if (tierPoints < 500) {
            return MembershipLevel.SILVER;
        } else {
            return MembershipLevel.GOLD;
        }
    }

    @Transient
    public Long getPointsToNextTierLevel() {
        MembershipLevel currentLevel = getTierBasedMembershipLevel();
        Long currentPoints = getTierPoints();

        switch (currentLevel) {
            case BRONZE:
                return Math.max(0L, 100L - currentPoints);
            case SILVER:
                return Math.max(0L, 500L - currentPoints);
            case GOLD:
            default:
                return 0L; // Already at highest level
        }
    }

    @Transient
    public MembershipLevel getNextMembershipLevel() {
        return getMembershipLevel().getNextLevel();
    }

    public void initializeScoreFields() {
        if (this.tierScore == null) {
            this.tierScore = this.addScore != null ? this.addScore : 0L;
        }
        if (this.exchangeScore == null) {
            this.exchangeScore = getAvailablePoints();
        }
    }


}
