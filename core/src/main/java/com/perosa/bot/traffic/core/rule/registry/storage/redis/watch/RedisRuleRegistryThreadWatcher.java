package com.perosa.bot.traffic.core.rule.registry.storage.redis.watch;

import com.perosa.bot.traffic.core.rule.registry.RuleRegistry;
import com.perosa.bot.traffic.core.rule.registry.storage.redis.RedisRuleRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;

public class RedisRuleRegistryThreadWatcher implements RedisRuleRegistryWatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisRuleRegistryThreadWatcher.class);

    private RedisRuleRegistry redisRuleRegistry;

    public RedisRuleRegistryThreadWatcher(RedisRuleRegistry redisRuleRegistry) {
        this.redisRuleRegistry = redisRuleRegistry;
    }

    @Override
    public void startWatch() {

        TimerTask task = new TimerTask() {
            public void run() {
                doAction();
            }
        };
        Timer timer = new Timer("redisRuleRegistryThreadWatcher");
        long delay = 1000L;
        long period = 5000L;

        timer.schedule(task, delay, period);

    }

    void doAction() {
        new RuleRegistry().setRules(redisRuleRegistry.load());
    }
}
