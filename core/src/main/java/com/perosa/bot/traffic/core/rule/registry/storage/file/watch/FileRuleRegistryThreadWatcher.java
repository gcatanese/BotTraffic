package com.perosa.bot.traffic.core.rule.registry.storage.file.watch;

import com.perosa.bot.traffic.core.common.EnvConfiguration;
import com.perosa.bot.traffic.core.rule.registry.storage.file.FileRuleRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;

public class FileRuleRegistryThreadWatcher implements FileRuleRegistryWatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileRuleRegistryThreadWatcher.class);

    private String location;

    public FileRuleRegistryThreadWatcher(String location) {
        this.location = location;
    }

    @Override
    public void startWatch() {

        TimerTask task = new TimerTask() {
            public void run() {
                doAction();
            }
        };
        Timer timer = new Timer("FileRuleRegistryThreadWatcher");
        long delay = 1000L;
        long period = new EnvConfiguration().getThreadWatchInterval();

        timer.schedule(task, delay, period);

    }


    void doAction() {
        FileRuleRegistry fileRuleRegistry = new FileRuleRegistry("src/test/resources/rules.json");
        fileRuleRegistry.setRules(fileRuleRegistry.load());

    }
}


