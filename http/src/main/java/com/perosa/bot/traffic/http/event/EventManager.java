package com.perosa.bot.traffic.http.event;

import com.perosa.bot.traffic.http.event.type.prometheus.PrometheusEvent;
import com.perosa.bot.traffic.http.event.type.prometheus.PrometheusEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventManager.class);

    public static void sendEvent(Event event) {
        new PrometheusEventHandler().process((PrometheusEvent)event);
    }
}


