package com.perosa;


import com.perosa.bot.traffic.core.rule.registry.watch.RuleRegistryWatcher;
import com.perosa.bot.traffic.core.service.registry.watch.ServiceRegistryWatcher;
import com.perosa.bot.traffic.http.metrics.prometheus.MetricsHandler;
import com.perosa.bot.traffic.http.server.dispatch.Dispatcher;
import com.perosa.bot.traffic.http.server.ReverseProxy;
import io.prometheus.client.hotspot.DefaultExports;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class BotTrafficApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(BotTrafficApp.class);

    public static void main(String[] args) {

        DefaultExports.initialize();

        new ReverseProxy(new Dispatcher()).setUp();
        new MetricsHandler().setUp();

        RuleRegistryWatcher.init();
        ServiceRegistryWatcher.init();

        Runtime.getRuntime().addShutdownHook(new TerminationManager());

        LOGGER.info("BotTraffic is up!");

    }

}

class TerminationManager extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(TerminationManager.class);

    public void run() {
        LOGGER.info("Come back soon!");
    }
}

