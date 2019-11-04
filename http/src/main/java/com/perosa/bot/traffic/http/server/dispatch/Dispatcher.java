package com.perosa.bot.traffic.http.server.dispatch;

import com.perosa.bot.traffic.http.server.dispatch.request.GetRequest;
import com.perosa.bot.traffic.http.server.dispatch.request.PostRequest;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Dispatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(Dispatcher.class);

    public void dispatch(HttpServerExchange exchange) {
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
