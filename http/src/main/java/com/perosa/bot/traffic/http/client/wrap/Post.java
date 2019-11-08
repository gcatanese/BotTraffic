package com.perosa.bot.traffic.http.client.wrap;

import java.util.HashMap;
import java.util.Map;

public class Post {

    private String url = "";
    private String path = "";
    private Map<String, String> headers = new HashMap<>();
    private String body = "";

    public Post(String url, String path) {
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Post[" + url + path + "]";
    }
}
