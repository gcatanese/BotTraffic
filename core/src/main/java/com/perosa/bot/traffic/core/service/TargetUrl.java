package com.perosa.bot.traffic.core.service;

public class TargetUrl {

    public TargetUrl() {}

    public TargetUrl(String url) {
        this.url = url;
    }

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "TargetUrl[" +
                "url:" + url +
                "]";
    }
}
