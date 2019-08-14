package com.perosa.bot.traffic.core;

import java.util.HashMap;
import java.util.Map;

public class BotProxyRequest {

    private String url;
    private String body;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String[]> parameters = new HashMap<>();

    public BotProxyRequest() {

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void addHeader(String key, String val) {
        this.headers.put(key, val);
    }

    public Map<String, String[]> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String[]> parameters) {
        this.parameters = parameters;
    }

}
