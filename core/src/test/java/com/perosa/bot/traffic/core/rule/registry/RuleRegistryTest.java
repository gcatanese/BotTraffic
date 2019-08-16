package com.perosa.bot.traffic.core.rule.registry;

import com.perosa.bot.traffic.core.rule.Rule;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RuleRegistryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleRegistryTest.class);

    RuleRegistry ruleRegistry = new RuleRegistry();

    @Test
    void getRule() {

        Rule rule = ruleRegistry.getRule("R0001");

        assertNotNull(rule);
        assertEquals("/webhook1", rule.getPath());

    }

    @Test
    void getLocation() {

        assertNotNull(RuleRegistry.getLocation());

    }

}
