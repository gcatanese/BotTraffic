package com.perosa.bot.traffic.http.metrics.prometheus;

import com.networknt.metrics.prometheus.PrometheusGetHandler;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.util.Methods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MetricsHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetricsHandler.class);

    private static Undertow builder = null;

    public void setUp() {
        if (builder == null) {
            LOGGER.info("starting MetricsHandler");

            builder = Undertow.builder()
                    .addHttpListener(8082, "localhost")
                    .setHandler(getHttpHandler())
                    .build();

            builder.start();
        }
    }

    private HttpHandler getHttpHandler() {
        return Handlers.routing()
                .add(Methods.GET, "/metrics", new PrometheusGetHandler());

    }

}
