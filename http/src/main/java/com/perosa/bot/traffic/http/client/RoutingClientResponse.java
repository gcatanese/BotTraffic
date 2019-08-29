package com.perosa.bot.traffic.http.client;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RoutingClientResponse {

    private int responseCode;
    private String body;
    private Map<String, List<String>> headers = Collections.emptyMap();

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    public String getContentType() {
        String contentType = "";

        if(getHeaders().get("Content-Type") != null && !getHeaders().get("Content-Type").isEmpty() && getHeaders().get("Content-Type").get(0) != null) {
            contentType = getHeaders().get("Content-Type").get(0);
        }
        return contentType;
    }

    public String getContentLength() {
        String contentLength = "";

        if(getHeaders().get("Content-Length") != null && !getHeaders().get("Content-Length").isEmpty() && getHeaders().get("Content-Length").get(0) != null) {
            contentLength = getHeaders().get("Content-Length").get(0);
        }
        return contentLength;
    }


    @Override
    public String toString() {
        return "RoutingClientResponse[" +
                "responseCode:" + responseCode +
                ", body:" + body +
                "]";
    }
}
