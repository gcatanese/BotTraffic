package com.perosa.bot.traffic.http.client.wrap;

import java.util.HashMap;
import java.util.Map;

public class Get {

    private String url;
    private String path;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String[]> parameters = new HashMap<>();

    public Get(String url, String path) {
        this.url = url;
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void setParameters(Map<String, String[]> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "Get[" + url + path + "]";
    }
}
