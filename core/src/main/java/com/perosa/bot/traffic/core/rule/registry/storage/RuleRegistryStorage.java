package com.perosa.bot.traffic.core.rule.registry.storage;

import com.perosa.bot.traffic.core.common.EnvConfiguration;
import com.perosa.bot.traffic.core.rule.Rule;
import com.perosa.bot.traffic.core.rule.registry.storage.file.FileRuleRegistry;
import com.perosa.bot.traffic.core.rule.registry.storage.redis.RedisRuleRegistry;
import redis.clients.jedis.Jedis;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public interface RuleRegistryStorage {

    List<Rule> load();

    void save(String json);

    static RuleRegistryStorage make() {

        RuleRegistryStorage ruleRegistryStorage = null;

        String storage = new EnvConfiguration().getStorage();

        if (storage.equalsIgnoreCase("redis")) {

            String redisUrl = new EnvConfiguration().getRedisUrl();

            try {
                ruleRegistryStorage = new RedisRuleRegistry(new Jedis(new URI(redisUrl)));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            ruleRegistryStorage = new FileRuleRegistry();
        }

        return ruleRegistryStorage;
    }
}
