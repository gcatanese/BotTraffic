package com.perosa.bot.traffic.http.client;

import com.networknt.client.Http2Client;
import com.networknt.client.rest.LightRestClient;
import com.perosa.bot.traffic.http.client.wrap.Get;
import com.perosa.bot.traffic.http.client.wrap.Post;
import io.undertow.client.ClientResponse;
import io.undertow.util.HeaderMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LightRestClientImpl implements Forwarder {

    private static final Logger LOGGER = LoggerFactory.getLogger(LightRestClientImpl.class);

    public ForwarderResponse get(Get input) throws Exception {

        ForwarderResponse forwarderResponse = new ForwarderResponse();

        LightRestClient lightRestClient = new LightRestClient();

        ClientResponse clientResponse = lightRestClient.get(input.getUrl(), input.getPath(),
                ClientResponse.class, input.getHeaders());

        if (clientResponse != null) {
            forwarderResponse.setResponseCode(clientResponse.getResponseCode());
            forwarderResponse.setBody(clientResponse.getAttachment(Http2Client.RESPONSE_BODY));
            forwarderResponse.setHeaders(getHeaders(clientResponse.getResponseHeaders()));

            LOGGER.info("routingClientResponse: " + forwarderResponse);
        } else {
            LOGGER.warn("clientResponse null");
        }


        return forwarderResponse;
    }

    public ForwarderResponse post(Post input) throws Exception {

        ForwarderResponse forwarderResponse = new ForwarderResponse();

        LightRestClient lightRestClient = new LightRestClient();

        ClientResponse clientResponse = lightRestClient.post(input.getUrl(), input.getPath(),
                ClientResponse.class, input.getHeaders(), input.getBody());

        if (clientResponse != null) {
            forwarderResponse.setResponseCode(clientResponse.getResponseCode());
            forwarderResponse.setBody(clientResponse.getAttachment(Http2Client.RESPONSE_BODY));
            forwarderResponse.setHeaders(getHeaders(clientResponse.getResponseHeaders()));

            LOGGER.info("routingClientResponse: " + forwarderResponse);
        } else {
            LOGGER.warn("clientResponse null");
        }

        return forwarderResponse;

    }

    Map<String, List<String>> getHeaders(HeaderMap headerMap) {
        Map<String, List<String>> headers = new HashMap<>();

        headerMap.getHeaderNames().stream()
                .forEach(m -> headers.put(m.toString(),
                        Arrays.asList(headerMap.get(m).toArray())
                ));

        return headers;
    }

}
