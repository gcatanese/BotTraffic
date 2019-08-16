package com.perosa.bot.traffic.http.metrics.prometheus;

import io.prometheus.client.Counter;
import io.prometheus.client.Summary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Summaries {

    private static final Logger LOGGER = LoggerFactory.getLogger(Summaries.class);

    static Map<String, Summary> summaries = new HashMap<>();

    public void observeResponseSize(String name, double size) {

        name = name + "_response_bytes";

        LOGGER.info("observe " + name);

        Summary receivedBytes = summaries.get(name);

        if(receivedBytes == null) {
            receivedBytes = Summary.build()
                    .name(name).help(name).register();

            summaries.put(name, receivedBytes);

        }

        receivedBytes.observe(size);

    }

    public void observeResponseDuration(String name, long duration) {

        name = name + "_response_duration_milliseconds";

        LOGGER.info("observe " + name);

        Summary durationMilliseconds = summaries.get(name);

        if(durationMilliseconds == null) {
            durationMilliseconds = Summary.build()
                    .name(name).help(name).register();

            summaries.put(name, durationMilliseconds);

        }

        durationMilliseconds.observe(duration);

    }

}
