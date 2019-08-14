package com.perosa.bot.traffic.http.event;

import com.perosa.bot.traffic.http.event.type.prometheus.PrometheusEvent;
import com.perosa.bot.traffic.http.event.type.prometheus.PrometheusEventHandler;
import net.engio.mbassy.listener.Handler;
import net.engio.mbassy.listener.Invoke;
import net.engio.mbassy.listener.Listener;
import net.engio.mbassy.listener.References;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Listener(references = References.Strong)
public class EventListener{

    private static final Logger LOGGER = LoggerFactory.getLogger(EventListener.class);

    @Handler(delivery = Invoke.Asynchronously)
    public void handle(Event event){
        LOGGER.info("handle " + event.getName());

        if(event instanceof PrometheusEvent) {
            new PrometheusEventHandler().process((PrometheusEvent)event);
        }
    }

}