package com.perosa.bot.traffic.http.event.type.prometheus;

import com.perosa.bot.traffic.http.metrics.prometheus.Counters;

public class PrometheusEventHandler {

    public void process(PrometheusEvent event) {

        if(event.getResponseCode() == 200) {
            increaseSuccessCounter(event.getUrl());
        } else {
            increaseErrorCounter(event.getUrl());
        }

    }

    void increaseSuccessCounter(String url) {
        new Counters().increaseSuccess(url);
    }

    void increaseErrorCounter(String url) {
        new Counters().increaseError(url);
    }

}
