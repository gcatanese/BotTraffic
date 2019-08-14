package com.perosa.bot.traffic.http.client;

import com.perosa.bot.traffic.http.client.wrap.Get;
import com.perosa.bot.traffic.http.client.wrap.Post;
import com.perosa.bot.traffic.http.event.EventManager;
import com.perosa.bot.traffic.http.event.type.prometheus.PrometheusEvent;
import com.networknt.client.rest.LightRestClient;
import io.undertow.client.ClientResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Router {

    private static final Logger LOGGER = LoggerFactory.getLogger(Router.class);

    public ClientResponse get(Get input) throws Exception {

        ClientResponse clientResponse = null;

        try {

            LightRestClient lightRestClient = new LightRestClient();

            Map<String, String> headerMap = new HashMap<>();
            headerMap.put("Content-Type", "application/json");
            headerMap.put("Transfer-Encoding", "chunked");

            clientResponse = lightRestClient.get(input.getUrl(), input.getPath(),
                    ClientResponse.class, headerMap);

            sendEvent(clientResponse, input.getUrl(), input.getPath());

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        LOGGER.info("clientResponse: " + clientResponse);
        return clientResponse;
    }


    public ClientResponse post(Post input) throws Exception {

        ClientResponse clientResponse = null;

        try {

            LightRestClient lightRestClient = new LightRestClient();

            clientResponse = lightRestClient.post(input.getUrl(), input.getPath(),
                    ClientResponse.class, input.getHeaders(), input.getBody());

            sendEvent(clientResponse, input.getUrl(), input.getPath());

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        LOGGER.info("clientResponse: " + clientResponse);
        return clientResponse;

    }

    void sendEvent(ClientResponse clientResponse, String url, String path) {

        url = url + path;

        PrometheusEvent event = new PrometheusEvent();
        event.setUrl(url);
        event.setResponseCode(clientResponse.getResponseCode());

        EventManager.sendEvent(event);

    }

}
