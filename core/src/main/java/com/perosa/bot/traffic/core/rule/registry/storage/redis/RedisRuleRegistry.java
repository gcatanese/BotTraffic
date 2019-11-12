package com.perosa.bot.traffic.core.rule.registry.storage.redis;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.perosa.bot.traffic.core.common.EnvConfiguration;
import com.perosa.bot.traffic.core.rule.Rule;
import com.perosa.bot.traffic.core.rule.registry.storage.RuleRegistryStorage;
import com.perosa.bot.traffic.core.rule.registry.storage.redis.watch.RedisRuleRegistryThreadWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class RedisRuleRegistry implements RuleRegistryStorage {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisRuleRegistry.class);

    private static final String REDIS_KEY_NAMESPACE = "bottraffic";

    private Jedis jedis;

    public RedisRuleRegistry(Jedis jedis) {
        this.jedis = jedis;
        new RedisRuleRegistryThreadWatcher(this).startWatch();
    }



    public List<Rule> load() {
        List<Rule> rules = new ArrayList<>();

        try {
            String key = REDIS_KEY_NAMESPACE + ":rules";
            rules = fetchFromRedis(key);

            LOGGER.info("Available rules: " + rules);

        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return rules;

    }

    @Override
    public void save(String json) {
        String key = REDIS_KEY_NAMESPACE + ":rules";
        sendToRedis(key, json);
    }

    List<Rule> unmarshal(String json) throws IOException {
        List<Rule> rules = null;

        ObjectMapper objectMapper = new ObjectMapper();

        rules = objectMapper
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .findAndRegisterModules()
                .readValue(json, new TypeReference<List<Rule>>() {
                });

        return rules;
    }

    List<Rule> fetchFromRedis(String key) throws IOException {
        List<Rule> rules = new ArrayList<>();

        String json = getJedis().get(key);

        if (json != null) {
            rules = unmarshal(json);
        }

        return rules;
    }

    private void sendToRedis(String key, String json) {
        getJedis().set(key, json);
        getJedis().expire(key, 60 * 30);
    }

    private void deleteFromRedis(String key) {

        long ret = getJedis().del(key);

        if (ret != 1) {
            LOGGER.warn("Error while DEL from Redis key:" + key + " ret:" + ret);
        }
    }

    private Set<String> getKeysFromRedis() {
        return getJedis().keys(REDIS_KEY_NAMESPACE + "*");
    }

    public Jedis getJedis() {
        return jedis;
    }

    public void setJedis(Jedis jedis) {
        this.jedis = jedis;
    }
}
