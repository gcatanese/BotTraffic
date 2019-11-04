package com.perosa.bot.traffic.core.service;

import com.perosa.bot.traffic.core.rule.RuleWorkflow;

public interface Consumable {

    String getHost();

    int getPort();

    String getId();

    String getUrl();

    int getWeight();

    RuleWorkflow getWorkflow();
}
