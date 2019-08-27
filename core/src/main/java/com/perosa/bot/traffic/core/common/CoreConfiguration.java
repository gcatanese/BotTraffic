package com.perosa.bot.traffic.core.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoreConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoreConfiguration.class);

    public String getHome() {
        String home = System.getProperty("bt.home");

        if (home == null || home.isEmpty()) {
            home = "config/";
        }

        if (!home.endsWith("/")) {
            home = home + "/";
        }


        return home;
    }

    public int getPort() {
        String port = System.getProperty("bt.port");

        if (port == null || port.isEmpty()) {
            port = System.getenv("PORT");
        }

        if (port == null || port.isEmpty()) {
            port = "8886";
        }


        return Integer.valueOf(port);
    }

    public int getMetricsHandlerPort() {
        String port = System.getProperty("bt.metricsport");

        if (port == null || port.isEmpty()) {
            port = "8887";
        }

        return Integer.valueOf(port);
    }

    public boolean isThreadWatch() {
        String watch = System.getProperty("bt.watch");

        return (watch !=null && watch.equalsIgnoreCase("thread"));
    }

    public int getThreadWatchInterval() {
        String interval = System.getProperty("bt.watchinterval");

        if (interval == null || interval.isEmpty()) {
            interval = "5000";
        }

        return Integer.valueOf(interval);
    }


}
