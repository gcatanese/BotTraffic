package com.perosa.bot.traffic.http.server;

import com.perosa.bot.traffic.http.server.request.GetRequest;
import com.perosa.bot.traffic.http.server.request.PostRequest;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReverseProxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReverseProxy.class);

    private static Undertow builder = null;

    public void setUp() {
        if (builder == null) {
            LOGGER.info("starting builder");
            builder = Undertow.builder()
                    .addHttpListener(8081, "localhost")
                    .setHandler(new HttpHandler() {
                        @Override
                        public void handleRequest(HttpServerExchange exchange) throws Exception {
                            LOGGER.debug("Incoming " + exchange.getRequestURL());
                            doHandleRequest(exchange);
                        }
                    })
                    .build();

            builder.start();

        }
    }

    private void doHandleRequest(HttpServerExchange exchange) {
        LOGGER.debug(exchange.toString());

        if (exchange.getRequestMethod().equals(new HttpString("POST"))) {
            new PostRequest().handle(exchange);
        } else if (exchange.getRequestMethod().equals(new HttpString("GET"))) {
            new GetRequest().handle(exchange);
        } else {
            LOGGER.error("Cannot handle " + exchange.getRequestMethod());
        }
    }



}
