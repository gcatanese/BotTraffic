package com.perosa;


import com.perosa.bot.traffic.http.metrics.prometheus.MetricsHandler;
import com.perosa.bot.traffic.http.server.ReverseProxy;
import io.prometheus.client.hotspot.DefaultExports;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {

        DefaultExports.initialize();

        new ReverseProxy().setUp();
        new MetricsHandler().setUp();

        LOGGER.info("App is up!");

    }

}
