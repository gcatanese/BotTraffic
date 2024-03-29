package com.perosa.bot.traffic.core.service.registry.watch;

import com.perosa.bot.traffic.core.service.registry.ServiceRegistry;
import com.perosa.bot.traffic.core.service.registry.ServiceRegistryLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServiceRegistryFileWatcher implements ServiceRegistryWatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRegistryFileWatcher.class);

    @Override
    public void startWatch() {

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {

            String filename = ServiceRegistry.getLocation();

            try {
                watch(filename);
            } catch (Exception e) {
                LOGGER.info(e.getMessage(), e);
            }

        });
    }

    void watch(String filename) throws Exception {

        Path file = Paths.get(filename);
        Path folder = file.getParent();

        LOGGER.debug("ServiceRegistryFileWatcher.watch " + folder.toAbsolutePath());

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
        new ServiceRegistry().setServiceInstances(new ServiceRegistryLoader().load());
    }
}
