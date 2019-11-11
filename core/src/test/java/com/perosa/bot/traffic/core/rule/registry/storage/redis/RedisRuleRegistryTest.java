package com.perosa.bot.traffic.core.rule.registry.storage.redis;

import com.perosa.bot.traffic.core.rule.Rule;
import com.perosa.bot.traffic.core.rule.registry.storage.file.FileRuleRegistry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RedisRuleRegistryTest {

    @Mock
    private Jedis jedis;

    @Test
    void fetchFromRedis() throws Exception {

        String key = "bottraffic:rules";
        RedisRuleRegistry redisRuleRegistry = new RedisRuleRegistry(jedis);
        when(jedis.get(eq(key))).thenReturn(getRulesAsJson());

        List<Rule> rules = redisRuleRegistry.fetchFromRedis(key);

        assertEquals(2, rules.size());
        verify(jedis, times(1)).get(isA(String.class));

    }

    @Test
    void fetchEmptyFromRedis() throws Exception {

        String key = "bottraffic:rules";
        RedisRuleRegistry redisRuleRegistry = new RedisRuleRegistry(jedis);
        when(jedis.get(eq(key))).thenReturn(null);

        List<Rule> rules = redisRuleRegistry.fetchFromRedis(key);

        assertEquals(0, rules.size());
        verify(jedis, times(1)).get(isA(String.class));

    }

    private String getRulesAsJson() {

        return "" +
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

    }

}