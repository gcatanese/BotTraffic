package com.perosa.bot.traffic.http.event;

import com.perosa.bot.traffic.http.event.type.prometheus.PrometheusEvent;
import org.junit.jupiter.api.Test;

public class EventManagerTest {

    @Test
    public void test() {
        Event event = new PrometheusEvent();
        EventManager.sendEvent(event);
    }
}
