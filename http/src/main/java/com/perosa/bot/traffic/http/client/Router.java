package com.perosa.bot.traffic.http.client;

import com.perosa.bot.traffic.core.common.UrlHelper;
import com.perosa.bot.traffic.http.client.wrap.Get;
import com.perosa.bot.traffic.http.client.wrap.Post;
import com.perosa.bot.traffic.http.event.EventManager;
import com.perosa.bot.traffic.http.event.type.prometheus.PrometheusEvent;
import io.undertow.client.ClientResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Router {

    private static final Logger LOGGER = LoggerFactory.getLogger(Router.class);

    private RoutingClient routingClient = new JavaClientImpl();

    public RoutingClientResponse get(Get input) throws Exception {

        long start = System.currentTimeMillis();

        RoutingClientResponse routingClientResponse = routingClient.get(input);

        long end = System.currentTimeMillis();

        sendEvent(routingClientResponse, input.getUrl(), end - start);

        LOGGER.info(routingClientResponse.toString());
        return routingClientResponse;
    }


    public RoutingClientResponse post(Post input) throws Exception {

        ClientResponse clientResponse = null;

        long start = System.currentTimeMillis();

        RoutingClientResponse routingClientResponse = routingClient.post(input);

        long end = System.currentTimeMillis();

        sendEvent(routingClientResponse, input.getUrl(), end - start);

        LOGGER.info(routingClientResponse.toString());
        return routingClientResponse;

    }

    void sendEvent(RoutingClientResponse routingClientResponse, String url, long duration) {

        if (routingClientResponse != null && routingClientResponse.getResponseCode() > 0) {

            PrometheusEvent event = new PrometheusEvent();
            event.setUrl(sanitize(url));
            event.setResponseCode(routingClientResponse.getResponseCode());
            event.setDuration(duration);

            String length = routingClientResponse.getContentLength();
            if (length != null) {
                event.setResponseSize(Double.valueOf(length));
            }

            EventManager.sendEvent(event);
        }

    }

    String sanitize(String url) {

        UrlHelper urlHelper = new UrlHelper();
        url = url.replace(urlHelper.getScheme(url) + "://", "");
        url = url.replace(".", "_");

        return url;
    }


}
