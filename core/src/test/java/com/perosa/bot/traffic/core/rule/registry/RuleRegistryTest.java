package com.perosa.bot.traffic.core.rule.registry;

import com.perosa.bot.traffic.core.rule.Rule;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RuleRegistryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleRegistryTest.class);

    RuleRegistry ruleRegistry = new RuleRegistry();

    @Test
    void getRule() {

        Rule rule = ruleRegistry.getRule("R0001");

        assertNotNull(rule);
        assertEquals("/webhook1", rule.getPath());
        assertTrue(rule.getStatus().isActive());
        assertTrue(rule.getWorkflow().isRoute());

    }

    @Test
    void getRuleAsJson() {

        Rule rule = ruleRegistry.getRule("R0001");

        String json = rule.asJson();

        assertNotNull(json);

        assertEquals("/webhook1", rule.getPath());
        assertTrue(json.contains("\"path\":\"/webhook1\","));
        assertTrue(json.contains("\"operator\":\"EQUAL_IGNORE_CASE\","));
    }

    @Test
    void getInactiveRule() {

        Rule rule = ruleRegistry.getRule("R0004");

        assertTrue(rule.getStatus().isInactive());

    }

    @Test
    void getParameterBasedRule() {

        Rule rule = ruleRegistry.getRule("R0004");

        assertTrue(rule.getType().isParameterRule());

    }

    @Test
    void getHeaderBasedRule() {

        Rule rule = ruleRegistry.getRule("R0005");

        assertTrue(rule.getType().isHeaderRule());

    }

    @Test
    void getLocation() {

        assertNotNull(RuleRegistry.getLocation());

    }

}
