package com.perosa.bot.traffic.http.event;

import org.junit.jupiter.api.Test;

public class EventManagerTest {

    @Test
    public void test() {
        Event event = new Event("test");
        EventManager.sendEvent(event);
    }
}
