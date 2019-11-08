package com.perosa.bot.traffic.http.server.dispatch.request;

import com.networknt.sanitizer.SanitizerHandler;
import com.perosa.bot.traffic.core.BotProxyRequest;
import com.perosa.bot.traffic.core.rule.worker.RuleWorker;
import com.perosa.bot.traffic.core.rule.worker.RuleWorkerImpl;
import com.perosa.bot.traffic.core.service.Consumable;
import com.perosa.bot.traffic.http.client.Forwarder;
import com.perosa.bot.traffic.http.server.dispatch.workflow.Router;
import com.perosa.bot.traffic.http.client.ForwarderResponse;
import com.perosa.bot.traffic.http.client.wrap.Get;
import com.perosa.bot.traffic.http.server.dispatch.workflow.Shadower;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetRequest extends ParentRequest implements Request {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParentRequest.class);

    private Forwarder forwarder;
    private Router router;
    private Shadower shadower;
    private RuleWorker ruleWorker;

    public GetRequest() {
        this.forwarder = Forwarder.getInstance();
        this.router = new Router(getForwarder());
        this.shadower = new Shadower(getForwarder());
        this.ruleWorker = new RuleWorkerImpl();
    }

    public GetRequest(Router router, Shadower shadower) {
        this();
        this.router = router;
        this.shadower = shadower;
    }

    public GetRequest(Router router, Shadower shadower, RuleWorker ruleWorker) {
        this(router, shadower);
        this.ruleWorker = ruleWorker;
    }

    public void handle(HttpServerExchange exchange) {

        Get get = null;

        try {

            BotProxyRequest request = initBotProxyRequest(exchange);

            Consumable consumable = getRuleWorker().process(request);

            get = initGet(consumable, request);

            if (consumable.isRouting()) {

                ForwarderResponse forwarderResponse = getRouter().get(get);

                if(forwarderResponse.getContentType() != null) {
                    exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, forwarderResponse.getContentType());
                }

                if(exchange.getResponseSender() != null) {
                    exchange.getResponseSender().send(forwarderResponse.getBody());
                }

            } else if (consumable.isShadowing()) {
                getShadower().get(get);

                if(exchange.getResponseSender() != null) {
                    exchange.getResponseSender().send("Got it");
                }
            }

        } catch (Exception e) {
            if (e instanceof com.networknt.client.rest.RestClientException) {
                LOGGER.error("Error invoking " + get.toString() + " ->" + e.getMessage());
            } else {
                LOGGER.error(e.getMessage(), e);
            }
        }

    }

    BotProxyRequest initBotProxyRequest(HttpServerExchange exchange) {
        BotProxyRequest request = new BotProxyRequest();

        request.setUrl(exchange.getRequestURL() + "?" + exchange.getQueryString());
        request.setHeaders(extractHeaders(exchange));
        request.setParameters(extractParameters(exchange));

        return request;
    }

    Get initGet(Consumable consumable, BotProxyRequest request) {

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

    public Forwarder getForwarder() {
        return forwarder;
    }

    public Router getRouter() {
        return router;
    }

    public Shadower getShadower() {
        return shadower;
    }

    public RuleWorker getRuleWorker() {
        return ruleWorker;
    }
}
