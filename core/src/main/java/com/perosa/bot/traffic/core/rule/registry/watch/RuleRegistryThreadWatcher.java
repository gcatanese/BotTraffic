package com.perosa.bot.traffic.core.rule.registry.watch;

import com.perosa.bot.traffic.core.common.EnvConfiguration;
import com.perosa.bot.traffic.core.rule.registry.RuleRegistry;
import com.perosa.bot.traffic.core.rule.registry.RuleRegistryLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;

public class RuleRegistryThreadWatcher implements RuleRegistryWatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleRegistryThreadWatcher.class);

    @Override
    public void startWatch() {

        TimerTask task = new TimerTask() {
            public void run() {
                doAction();
            }
        };
        Timer timer = new Timer("RuleRegistryWatch");
        long delay = 1000L;
        long period = new EnvConfiguration().getThreadWatchInterval();

        timer.schedule(task, delay, period);

    }


    void doAction() {
        new RuleRegistry().setRules(new RuleRegistryLoader().load());

    }
}


