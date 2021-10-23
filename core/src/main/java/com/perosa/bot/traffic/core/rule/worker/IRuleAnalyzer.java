package com.perosa.bot.traffic.core.rule.worker;

import com.perosa.bot.traffic.core.rule.Rule;

public interface IRuleAnalyzer {
    String findValue(Rule rule);

    String findValueInBody(String elementPath);

    String findValueInParameters(String parameter);

    String findValueInHeaders(String parameter);
}
