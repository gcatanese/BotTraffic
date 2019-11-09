package com.perosa.bot.traffic.http.server.dispatch.workflow;

import com.perosa.bot.traffic.core.common.UrlHelper;
import com.perosa.bot.traffic.http.client.Forwarder;
import com.perosa.bot.traffic.http.client.ForwarderResponse;
import com.perosa.bot.traffic.http.client.JavaClientImpl;
import com.perosa.bot.traffic.http.client.wrap.Get;
import com.perosa.bot.traffic.http.client.wrap.Post;
import com.perosa.bot.traffic.http.event.EventManager;
import com.perosa.bot.traffic.http.event.type.prometheus.PrometheusEvent;
import io.undertow.client.ClientResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Router {

    private static final Logger LOGGER = LoggerFactory.getLogger(Router.class);

    private Forwarder forwarder;

    public Router(Forwarder forwarder) {
        this.forwarder = forwarder;
    }

    public ForwarderResponse get(Get input) throws Exception {

        long start = System.currentTimeMillis();

        ForwarderResponse forwarderResponse = forwarder.get(input);

        long end = System.currentTimeMillis();

        sendEvent(forwarderResponse, input.getUrl(), end - start);

        LOGGER.info(forwarderResponse.toString());
        return forwarderResponse;
    }


    public ForwarderResponse post(Post input) throws Exception {

        long start = System.currentTimeMillis();

        ForwarderResponse forwarderResponse = getForwarder().post(input);

        long end = System.currentTimeMillis();

        sendEvent(forwarderResponse, input.getUrl(), end - start);

        LOGGER.info(forwarderResponse.toString());
        return forwarderResponse;

    }

    void sendEvent(ForwarderResponse forwarderResponse, String url, long duration) {

        if (forwarderResponse != null && forwarderResponse.getResponseCode() > 0) {

            PrometheusEvent event = new PrometheusEvent();
            event.setUrl(sanitize(url));
            event.setResponseCode(forwarderResponse.getResponseCode());
            event.setDuration(duration);

            String length = forwarderResponse.getContentLength();
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

    public Forwarder getForwarder() {
        return forwarder;
    }
}
