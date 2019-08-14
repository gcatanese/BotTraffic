package com.perosa.bot.traffic.core.rule;

public enum RuleStatus {
    ACTIVE, INACTIVE;

    public boolean isActive() {
        return this.equals(RuleStatus.ACTIVE);
    }

    public boolean isInactive() {
        return this.equals(RuleStatus.INACTIVE);
    }

}