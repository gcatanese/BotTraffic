package com.perosa.bot.traffic.http.event.type.prometheus;

import com.perosa.bot.traffic.http.event.Event;

public class PrometheusEvent extends Event {

    private String url;
    private int responseCode;

    public PrometheusEvent() {
        super("prometheus");
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }
}
