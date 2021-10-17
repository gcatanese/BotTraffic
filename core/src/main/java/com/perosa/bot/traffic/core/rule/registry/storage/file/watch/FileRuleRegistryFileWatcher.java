package com.perosa.bot.traffic.core.rule.registry.storage.file.watch;

import com.perosa.bot.traffic.core.rule.registry.storage.file.FileRuleRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileRuleRegistryFileWatcher implements FileRuleRegistryWatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileRuleRegistryFileWatcher.class);

    private String location;

    public FileRuleRegistryFileWatcher(String location) {
        this.location = location;
    }

    @Override
    public void startWatch() {

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {

            try {
                watch(this.location);
            } catch (Exception e) {
                LOGGER.info(e.getMessage(), e);
            }

        });
    }

    void watch(String filename) throws Exception {

        Path file = Paths.get(filename);
        Path folder = file.getParent();

        LOGGER.debug("FileRuleRegistryFileWatcher.watch " + folder.toAbsolutePath());

        WatchService watchService = FileSystems.getDefault().newWatchService();

        folder.register(
                watchService,
                StandardWatchEventKinds.ENTRY_MODIFY);

        WatchKey key;
        while ((key = watchService.take()) != null) {
            for (WatchEvent<?> event : key.pollEvents()) {
                LOGGER.debug(event.kind() + "--> " + event.context() + ".");
                if (event.context().toString().equalsIgnoreCase(file.getFileName().toString())) {
                    doAction();
                }
            }
            key.reset();
        }

    }

    void doAction() {
        FileRuleRegistry fileRuleRegistry = new FileRuleRegistry(location);
        fileRuleRegistry.setRules(fileRuleRegistry.load());
    }

}


