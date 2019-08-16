package com.perosa.bot.traffic.http.event.type.prometheus;

import com.perosa.bot.traffic.http.metrics.prometheus.Counters;
import com.perosa.bot.traffic.http.metrics.prometheus.Summaries;

public class PrometheusEventHandler {

    public void process(PrometheusEvent event) {

        trackResponse(event);
        trackResponseSize(event);
        trackResponseDuration(event);

    }

    private void trackResponse(PrometheusEvent event) {
        if(event.getResponseCode() == 200) {
            increaseSuccessCounter(event.getUrl());
        } else {
            increaseErrorCounter(event.getUrl());
        }
    }

    private void trackResponseSize(PrometheusEvent event) {
        if(event.getResponseCode() == 200) {
            if(event.getResponseSize() > 0) {
                new Summaries().observeResponseSize(event.getUrl(), event.getResponseSize());
            }
        }
    }

    private void trackResponseDuration(PrometheusEvent event) {
        if(event.getResponseCode() == 200) {
            if(event.getDuration() > 0) {
                new Summaries().observeResponseDuration(event.getUrl(), event.getDuration());
            }
        }
    }

    private void increaseSuccessCounter(String url) {
        new Counters().increaseSuccess(url);
    }

    private void increaseErrorCounter(String url) {
        new Counters().increaseError(url);
    }

}
