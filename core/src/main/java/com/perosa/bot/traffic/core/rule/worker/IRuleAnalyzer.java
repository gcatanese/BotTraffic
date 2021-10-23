package com.perosa.bot.traffic.core.rule.worker;

import com.perosa.bot.traffic.core.rule.Rule;

public interface IRuleAnalyzer {

    /**
     * Find value for the given expression
     * @param rule Instance of Rule
     * @return
     */
    String findValue(Rule rule);

    /**
     * Find value in body for the given expression
     * @param elementPath
     * @return
     */
    String findValueInBody(String elementPath);

    /**
     * Find value in request parameters for the given expression
     * @param parameter
     * @return
     */
    String findValueInParameters(String parameter);

    /**
     * Find value in HTTP headers for the given expression
     * @param parameter
     * @return
     */
    String findValueInHeaders(String parameter);
}
