package com.perosa.bot.traffic.core.service.registry.watch;

import com.perosa.bot.traffic.core.common.EnvConfiguration;
import com.perosa.bot.traffic.core.service.registry.ServiceRegistry;
import com.perosa.bot.traffic.core.service.registry.ServiceRegistryLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;

public class ServiceRegistryThreadWatcher implements ServiceRegistryWatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRegistryThreadWatcher.class);

    public ServiceRegistryThreadWatcher() {

        LOGGER.debug("ServiceRegistryThreadWatcher.watch ");

    }
    @Override
    public void startWatch() {

        TimerTask task = new TimerTask() {
            public void run() {
                doAction();
            }
        };
        Timer timer = new Timer("ServiceRegistryThreadWatcher");
        long delay = 1000L;
        long period = new EnvConfiguration().getThreadWatchInterval();

        timer.schedule(task, delay, period);

        LOGGER.debug("ServiceRegistryThreadWatcher start");

    }

    void doAction() {
        new ServiceRegistry().setServiceInstances(new ServiceRegistryLoader().load());
    }

}
