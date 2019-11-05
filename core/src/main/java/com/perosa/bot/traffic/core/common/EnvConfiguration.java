package com.perosa.bot.traffic.core.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnvConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnvConfiguration.class);

    public String getHome() {
        String home = System.getenv("BT_HOME");

        if (home == null || home.isEmpty()) {
            home = "config/";
        }

        if (!home.endsWith("/")) {
            home = home + "/";
        }


        return home;
    }

    public int getPort() {
        String port = System.getenv("BT_PORT");

        if (port == null || port.isEmpty()) {
            port = System.getenv("PORT");
        }

        if (port == null || port.isEmpty()) {
            port = "8886";
        }

        return Integer.valueOf(port);
    }

    public int getMetricsHandlerPort() {
        String port = System.getenv("BT_METRICS_PORT");

        if (port == null || port.isEmpty()) {
            port = "8887";
        }

        return Integer.valueOf(port);
    }

    public boolean isThreadWatch() {
        String watch = System.getenv("BT_WATCH");

        return (watch != null && watch.equalsIgnoreCase("thread"));
    }

    public int getThreadWatchInterval() {
        String interval = System.getenv("BT_WATCH_INTERVAL");

        if (interval == null || interval.isEmpty()) {
            interval = "5000";
        }

        return Integer.valueOf(interval);
    }


}
