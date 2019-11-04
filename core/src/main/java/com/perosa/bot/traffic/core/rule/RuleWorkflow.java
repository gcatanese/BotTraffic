package com.perosa.bot.traffic.core.rule;

public enum RuleWorkflow {
    ROUTE, SHADOW, FILTER;

    public boolean isRoute() {
        return this.equals(RuleWorkflow.ROUTE);
    }

    public boolean isShadow() {
        return this.equals(RuleWorkflow.SHADOW);
    }

    public boolean isFilter() {
        return this.equals(RuleWorkflow.FILTER);
    }

}