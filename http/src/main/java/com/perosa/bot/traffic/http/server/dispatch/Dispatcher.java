package com.perosa.bot.traffic.http.server.dispatch;

import com.perosa.bot.traffic.http.server.dispatch.request.GetRequest;
import com.perosa.bot.traffic.http.server.dispatch.request.PostRequest;
import com.perosa.bot.traffic.http.server.dispatch.request.Request;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Dispatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(Dispatcher.class);

    public void dispatch(HttpServerExchange exchange) {
        LOGGER.debug(exchange.toString());

        Request request = getRequestHandler(exchange);

        if(request != null) {
            request.handle(exchange);
        }

    }

    Request getRequestHandler(HttpServerExchange exchange) {

        Request request;

        if (exchange.getRequestMethod().equals(new HttpString("POST"))) {
            request = new PostRequest();
        } else if (exchange.getRequestMethod().equals(new HttpString("GET"))) {
            request = new GetRequest();
        } else {
            request = null;
            LOGGER.error("Cannot handle " + exchange.getRequestMethod());
        }

        return request;
    }
}
