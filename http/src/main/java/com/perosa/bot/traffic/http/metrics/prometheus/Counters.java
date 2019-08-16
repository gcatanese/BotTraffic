package com.perosa.bot.traffic.http.metrics.prometheus;

import io.prometheus.client.Counter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Counters {

    private static final Logger LOGGER = LoggerFactory.getLogger(Counters.class);

    static Map<String, Counter> counters = new HashMap<>();

    public void increaseSuccess(String url) {
        increase(url + "_success");
    }

    public void increaseError(String url) {
        increase(url + "_failure");
    }

    void increase(String name) {

        Counter counter = counters.get(name);

        if(counter == null) {
            counter = Counter.build()
                    .name(name)
                    .help(name).register();

            counters.put(name, counter);

        }

        counter.inc();

    }
}
