package com.perosa.bot.traffic.core.rule.registry.watch;

import com.perosa.bot.traffic.core.rule.registry.RuleRegistry;
import com.perosa.bot.traffic.core.rule.registry.RuleRegistryLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.*;

public class RuleRegistryFileWatcher implements RuleRegistryWatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleRegistryFileWatcher.class);


    @Override
    public void startWatch() {

        String filename = RuleRegistry.getLocation();

        try {
            watch(filename);
        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
        }
    }

    void watch(String filename) throws Exception {

        Path file = Paths.get(filename);
        Path folder = file.getParent();

        WatchService watchService = FileSystems.getDefault().newWatchService();

        folder.register(
                watchService,
                StandardWatchEventKinds.ENTRY_MODIFY);

        WatchKey key;
        while ((key = watchService.take()) != null) {
            for (WatchEvent<?> event : key.pollEvents()) {
                if (event.context().toString().equalsIgnoreCase(file.getFileName().toString())) {
                    doAction();
                }
            }
            key.reset();
        }

    }

    void doAction() {
        new RuleRegistry().setRules(new RuleRegistryLoader().load());

    }
}


