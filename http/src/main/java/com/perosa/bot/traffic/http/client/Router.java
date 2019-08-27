package com.perosa.bot.traffic.http.client;

import com.networknt.client.rest.LightRestClient;
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

    public ClientResponse get(Get input) throws Exception {

        ClientResponse clientResponse = null;

        long start = System.currentTimeMillis();

        LightRestClient lightRestClient = new LightRestClient();

        clientResponse = lightRestClient.get(input.getUrl(), input.getPath(),
                ClientResponse.class, input.getHeaders());

        long end = System.currentTimeMillis();

        sendEvent(clientResponse, input.getUrl(), end - start);

        LOGGER.info("clientResponse: " + clientResponse);
        return clientResponse;
    }


    public ClientResponse post(Post input) throws Exception {

        ClientResponse clientResponse = null;

        long start = System.currentTimeMillis();

        LightRestClient lightRestClient = new LightRestClient();

        clientResponse = lightRestClient.post(input.getUrl(), input.getPath(),
                ClientResponse.class, input.getHeaders(), input.getBody());

        long end = System.currentTimeMillis();

        if (clientResponse != null) {
            sendEvent(clientResponse, input.getUrl(), end - start);
        }

        LOGGER.info("clientResponse: " + clientResponse);
        return clientResponse;

    }

    void sendEvent(ClientResponse clientResponse, String url, long duration) {

        PrometheusEvent event = new PrometheusEvent();
        event.setUrl(sanitize(url));
        event.setResponseCode(clientResponse.getResponseCode());
        event.setDuration(duration);

        String length = clientResponse.getResponseHeaders().getFirst("Content-Length");
        if (length != null) {
            event.setResponseSize(Double.valueOf(length));
        }

        EventManager.sendEvent(event);

    }

    String sanitize(String url) {

        UrlHelper urlHelper = new UrlHelper();
        url = url.replace(urlHelper.getScheme(url) + "://", "");

        return url;
    }

}
