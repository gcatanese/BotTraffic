package com.perosa.bot.traffic.http.server.dispatch.request;

import com.perosa.bot.traffic.core.BotProxyRequest;
import com.perosa.bot.traffic.core.rule.worker.RuleWorker;
import com.perosa.bot.traffic.core.rule.worker.RuleWorkerImpl;
import com.perosa.bot.traffic.core.service.Consumable;
import com.perosa.bot.traffic.http.client.Forwarder;
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

    private Forwarder forwarder;
    private Router router;
    private Shadower shadower;
    private RuleWorker ruleWorker;

    public PostRequest() {
        this.forwarder = Forwarder.getInstance();
        this.router = new Router(getForwarder());
        this.shadower = new Shadower(getForwarder());
        // rule engine
        this.ruleWorker = new RuleWorkerImpl();
    }

    public void handle(HttpServerExchange exchange) {

        Post post = null;

        try {

            BotProxyRequest request = initBotProxyRequest(exchange);

            Consumable consumable = getRuleWorker().process(request);

            if(consumable != null) {

                post = initPost(consumable, request);

                if (consumable.isRouting()) {

                    ForwarderResponse forwarderResponse = getRouter().post(post);

                    String clientResponseAsString = forwarderResponse.getBody();

                    if (forwarderResponse.getContentType() != null) {
                        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, forwarderResponse.getContentType());
                    }

                    if (exchange.getResponseSender() != null) {
                        exchange.getResponseSender().send(clientResponseAsString);
                    }

                } else if (consumable.isShadowing()) {
                    getShadower().post(post);

                    if (exchange.getResponseSender() != null) {
                        exchange.getResponseSender().send("Got it");
                    }
                }
            }

        } catch (Exception e) {
            if (e instanceof com.networknt.client.rest.RestClientException) {
                LOGGER.error("Error invoking " + post.toString() + " ->" + e.getMessage());
            } else {
                LOGGER.error(e.getMessage(), e);
            }
        }

    }

    BotProxyRequest initBotProxyRequest(HttpServerExchange exchange) {

        BotProxyRequest request = new BotProxyRequest();

        request.setUrl(exchange.getRequestURL());
        request.setHeaders(extractHeaders(exchange));
        request.setBody(extractBody(exchange));

        return request;

    }

    Post initPost(Consumable consumable, BotProxyRequest request) {
        Post post = new Post(getUrl(consumable.getUrl()), getPath(consumable.getUrl()));

        if (!request.getBody().isEmpty()) {
            post.setBody(request.getBody());
        }
        if (!request.getHeaders().isEmpty()) {
            post.setHeaders(request.getHeaders());
        }

        return post;
    }

    public Forwarder getForwarder() {
        return forwarder;
    }

    public void setForwarder(Forwarder forwarder) {
        this.forwarder = forwarder;
    }

    public Router getRouter() {
        return router;
    }

    public void setRouter(Router router) {
        this.router = router;
    }

    public Shadower getShadower() {
        return shadower;
    }

    public void setShadower(Shadower shadower) {
        this.shadower = shadower;
    }

    public RuleWorker getRuleWorker() {
        return ruleWorker;
    }

    public void setRuleWorker(RuleWorker ruleWorker) {
        this.ruleWorker = ruleWorker;
    }
}
