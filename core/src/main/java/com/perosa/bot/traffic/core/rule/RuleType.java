package com.perosa.bot.traffic.core.rule;

public enum RuleType {
    BODY, HEADER, PARAMETER;

    public boolean isBodyRule() {
        return this.equals(RuleType.BODY);
    }

    public boolean isHeaderRule() {
        return this.equals(RuleType.HEADER);
    }

    public boolean isParameterRule() {
        return this.equals(RuleType.PARAMETER);
    }

}