package com.perosa.bot.traffic.http.server.request;

import com.perosa.bot.traffic.core.BotProxyRequest;
import com.perosa.bot.traffic.core.rule.worker.RuleWorkerImpl;
import com.perosa.bot.traffic.core.service.Consumable;
import com.perosa.bot.traffic.http.client.Router;
import com.perosa.bot.traffic.http.client.RoutingClientResponse;
import com.perosa.bot.traffic.http.client.wrap.Get;
import com.networknt.client.Http2Client;
import io.undertow.client.ClientResponse;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetRequest extends ParentRequest implements Request {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParentRequest.class);

    public void handle(HttpServerExchange exchange) {

        Get get = null;

        try {

            BotProxyRequest request = initBotProxyRequest(exchange);

            Consumable consumable = new RuleWorkerImpl(request).process();

            get = initGet(consumable, request);

            RoutingClientResponse routingClientResponse = new Router().get(get);

            String clientResponseAsString = routingClientResponse.getBody();

            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, routingClientResponse.getContentType());
            exchange.getResponseSender().send(clientResponseAsString);

        } catch (Exception e) {
            if (e instanceof com.networknt.client.rest.RestClientException) {
                LOGGER.error("Error invoking " + get.toString() + " ->" + e.getMessage());
            } else {
                LOGGER.error(e.getMessage(), e);
            }
        }

    }

    private BotProxyRequest initBotProxyRequest(HttpServerExchange exchange) {
        BotProxyRequest request = new BotProxyRequest();

        request.setUrl(exchange.getRequestURL() + "?" + exchange.getQueryString());
        request.setHeaders(extractHeaders(exchange));
        request.setParameters(extractParameters(exchange));

        return request;
    }

    private Get initGet(Consumable consumable, BotProxyRequest request) {

        Get get = new Get(getUrl(consumable.getUrl()), getPath(consumable.getUrl())
                + getQueryString(consumable.getUrl()));

        if (!request.getHeaders().isEmpty()) {
            get.setHeaders(request.getHeaders());
        }
        if (!request.getParameters().isEmpty()) {
            get.setParameters(request.getParameters());
        }

        return get;
    }


}
