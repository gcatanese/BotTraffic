package com.perosa.bot.traffic.http.server.request;

import io.undertow.server.HttpServerExchange;

public interface Request {

    void handle(HttpServerExchange exchange);
}
