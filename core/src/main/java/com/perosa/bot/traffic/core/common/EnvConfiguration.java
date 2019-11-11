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

    public boolean isFallbackRequestedUrl() {
        String fallback = System.getenv("BT_FALLBACK_REQUESTED_URL");

        return (fallback != null && fallback.equalsIgnoreCase("true"));
    }

    public String getStorage() {
        String storage = System.getenv("BT_STORAGE");

        if (storage == null || storage.isEmpty()) {
            storage = "file";
        }

        if (storage.equalsIgnoreCase("file") && storage.equalsIgnoreCase("redis")) {
            LOGGER.error("Invalid BT_STORAGE attribute:" + storage);
        }

        return storage;
    }

    public String getRedisHost() {
        String host = System.getenv("BT_REDIS_HOST");

        if (host == null || host.isEmpty()) {
            host = "localhost";
        }

        return host;
    }

    public int getRedisPort() {
        String port = System.getenv("BT_REDIS_PORT");

        if (port == null || port.isEmpty()) {
            port = "6379";
        }

        return Integer.valueOf(port);
    }

}
