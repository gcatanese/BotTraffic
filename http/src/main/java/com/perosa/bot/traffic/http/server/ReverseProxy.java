package com.perosa.bot.traffic.http.server;

import com.perosa.bot.traffic.core.common.EnvConfiguration;
import com.perosa.bot.traffic.http.server.dispatch.Dispatcher;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReverseProxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReverseProxy.class);

    private static Undertow builder = null;
    private Dispatcher dispatcher;

    public ReverseProxy(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void setUp() {

        final String host = "0.0.0.0";
        final int port = getPort();
        final String home = new EnvConfiguration().getHome();

        try {

            if (builder == null) {
                LOGGER.info("listening on http://localhost:" + port + " (bt.home: " + home + ")");

                builder = Undertow.builder()
                        .addHttpListener(port, host)
                        .setHandler(new HttpHandler() {
                            @Override
                            public void handleRequest(HttpServerExchange exchange) throws Exception {

                                if(isTest(exchange)) {
                                    sendTestReply(exchange);
                                } else {
                                    getDispatcher().dispatch(exchange);
                                }
                            }
                        })
                        .build();

                builder.start();

            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    int getPort() {
        return new EnvConfiguration().getPort();
    }

    boolean isTest(HttpServerExchange exchange) {
        return exchange.getRequestPath() != null && exchange.getRequestPath().endsWith("/test");
    }

    void sendTestReply(HttpServerExchange exchange) {
        LOGGER.info("/test");

        exchange.setStatusCode(200);
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
        exchange.getResponseSender().send("Ok");
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }
}
