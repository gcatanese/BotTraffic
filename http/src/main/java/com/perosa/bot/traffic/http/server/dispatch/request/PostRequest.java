package com.perosa.bot.traffic.http.server.dispatch.request;

import com.perosa.bot.traffic.core.BotProxyRequest;
import com.perosa.bot.traffic.core.rule.worker.RuleWorkerImpl;
import com.perosa.bot.traffic.core.service.Consumable;
import com.perosa.bot.traffic.http.server.dispatch.workflow.Router;
import com.perosa.bot.traffic.http.client.ForwarderResponse;
import com.perosa.bot.traffic.http.client.wrap.Post;
import com.perosa.bot.traffic.http.server.dispatch.workflow.Shadower;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PostRequest extends ParentRequest implements Request {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostRequest.class);

    public void handle(HttpServerExchange exchange) {

        Post post = null;

        try {

            BotProxyRequest request = initBotProxyRequest(exchange);

            Consumable consumable = new RuleWorkerImpl(request).process();

            post = initPost(consumable, request);

            if (consumable.isRouting()) {

                ForwarderResponse forwarderResponse = new Router().post(post);

                String clientResponseAsString = forwarderResponse.getBody();

                exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, forwarderResponse.getContentType());
                exchange.getResponseSender().send(clientResponseAsString);

            } else if (consumable.isShadowing()) {
                new Shadower().post(post);
                exchange.getResponseSender().send("Got it");
            }

        } catch (Exception e) {
            if (e instanceof com.networknt.client.rest.RestClientException) {
                LOGGER.error("Error invoking " + post.toString() + " ->" + e.getMessage());
            } else {
                LOGGER.error(e.getMessage(), e);
            }
        }

    }

    private BotProxyRequest initBotProxyRequest(HttpServerExchange exchange) {

        BotProxyRequest request = new BotProxyRequest();

        request.setUrl(exchange.getRequestURL());
        request.setHeaders(extractHeaders(exchange));
        request.setBody(extractBody(exchange));

        return request;

    }

    private Post initPost(Consumable consumable, BotProxyRequest request) {
        Post post = new Post(getUrl(consumable.getUrl()), getPath(consumable.getUrl()));

        if (!request.getBody().isEmpty()) {
            post.setBody(request.getBody());
        }
        if (!request.getHeaders().isEmpty()) {
            post.setHeaders(request.getHeaders());
        }

        return post;
    }

}
