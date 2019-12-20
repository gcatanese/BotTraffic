package com.perosa.bot.traffic.core.rule.registry.storage.file;

import com.perosa.bot.traffic.core.rule.Operator;
import com.perosa.bot.traffic.core.rule.Rule;
import com.perosa.bot.traffic.core.rule.RuleType;
import com.perosa.bot.traffic.core.service.ConsumableService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileRuleRegistryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileRuleRegistryTest.class);

    @Test
    void getJson() throws IOException {

        String json = new FileRuleRegistry().getJson("src/test/resources/rules.json");

        LOGGER.info(json);

        assertNotNull(json);

    }

    @Test
    void getLocation() {
        assertNotNull(new FileRuleRegistry().getLocation());
    }

    @Test
    void unmarshal() throws IOException {

        String json = "" +
                "[\n" +
                "  {\n" +
                "    \"id\": \"R0001\",\n" +
                "    \"path\": \"/webhook1\",\n" +
                "    \"expression\": \"/from/name\",\n" +
                "    \"operator\": \"EQUAL_IGNORE_CASE\",\n" +
                "    \"value\": \"Mr X\",\n" +
                "    \"type\": \"BODY\",\n" +
                "    \"status\": \"ACTIVE\",\n" +
                "    \"targets\": [\n" +
                "      {\n" +
                "        \"id\": \"s00001\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"s00002\"\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"R0002\",\n" +
                "    \"path\": \"/webhook1\",\n" +
                "    \"expression\": \"/from/name\",\n" +
                "    \"operator\": \"EQUAL_IGNORE_CASE\",\n" +
                "    \"value\": \"Mr Y\",\n" +
                "    \"type\": \"BODY\",\n" +
                "    \"status\": \"ACTIVE\",\n" +
                "    \"targets\": [\n" +
                "      {\n" +
                "        \"id\": \"s00003\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "]" +
                "]";

        List<Rule> rules = new FileRuleRegistry().unmarshal(json);

        assertNotNull(rules);
        assertEquals(2, rules.size());

    }

    @Test
    void load() throws IOException {

        List<Rule> rules = new FileRuleRegistry().load();

        assertNotNull(rules);
        assertTrue(rules.size() > 0);

    }

    @Test
    void loadAsMap() throws IOException {

        List<ConsumableService> targets = Arrays.asList(
                new ConsumableService("s1", "localhost", 8080),
                new ConsumableService("s2", "localhost", 8081));
        List<Rule> rules = Arrays.asList(
                new Rule("/p1", "/payload/name", "val1", Operator.EQUAL, RuleType.BODY,
                        Arrays.asList(
                                new ConsumableService("s1", "localhost", 8080),
                                new ConsumableService("s2", "localhost", 8081))
                ),
                new Rule("/p2", "/payload/ip", "val2", Operator.NOT_EQUAL, RuleType.BODY,
                        Arrays.asList(
                                new ConsumableService("s3", "localhost", 9090),
                                new ConsumableService("s4", "localhost", 9091),
                                new ConsumableService("s5", "localhost", 9093))
                )
        );

        Map<String, List<Rule>> map = new FileRuleRegistry().loadAsMap(rules);

        assertNotNull(rules);
        assertEquals(2, rules.size());
        // {0}
        assertEquals("/p1", rules.get(0).getPath());
        assertEquals(2, rules.get(0).getTargetServices().size());
        // {1}
        assertEquals("/p2", rules.get(1).getPath());
        assertEquals(3, rules.get(1).getTargetServices().size());

    }
}
