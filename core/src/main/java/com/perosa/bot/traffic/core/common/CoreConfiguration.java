package com.perosa.bot.traffic.core.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoreConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoreConfiguration.class);

    public String getHome() {
        String home = System.getProperty("bt.home");

        if(home == null || home.isEmpty()) {
            throw new RuntimeException("bt.home is undefined");
        }

        if(!home.endsWith("/")) {
            home = home + "/";
        }

        return home;
    }

    public int getPort() {
        String port = System.getProperty("bt.port");

        if(port == null || port.isEmpty()) {
            port = "8081";
        }

        return Integer.valueOf(port);
    }


}
