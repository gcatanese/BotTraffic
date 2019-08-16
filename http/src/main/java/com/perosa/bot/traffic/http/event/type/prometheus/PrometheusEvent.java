package com.perosa.bot.traffic.http.event.type.prometheus;

import com.perosa.bot.traffic.http.event.Event;

public class PrometheusEvent extends Event {

    private String url;
    private int responseCode;
    private double responseSize;
    private long duration;

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

    public double getResponseSize() {
        return responseSize;
    }

    public void setResponseSize(double responseSize) {
        this.responseSize = responseSize;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
