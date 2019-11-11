package com.perosa.bot.traffic.core.rule.registry.storage;

import com.perosa.bot.traffic.core.common.EnvConfiguration;
import com.perosa.bot.traffic.core.rule.Rule;
import com.perosa.bot.traffic.core.rule.registry.storage.file.FileRuleRegistry;
import com.perosa.bot.traffic.core.rule.registry.storage.redis.RedisRuleRegistry;
import redis.clients.jedis.Jedis;

import java.util.List;

public interface RuleRegistryStorage {

    List<Rule> load();

    static RuleRegistryStorage make() {

        String storage = new EnvConfiguration().getStorage();
        String redisHost = new EnvConfiguration().getRedisHost();
        int redisPort = new EnvConfiguration().getRedisPort();

        if (storage.equalsIgnoreCase("redis")) {
            return new RedisRuleRegistry(new Jedis(redisHost, redisPort));
        } else {
            return new FileRuleRegistry();
        }
    }
}
