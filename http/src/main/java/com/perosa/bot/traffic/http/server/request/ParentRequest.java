package com.perosa.bot.traffic.http.server.request;

import io.undertow.server.HttpServerExchange;
import io.undertow.util.HeaderMap;

import java.util.HashMap;
import java.util.Map;

public class ParentRequest {

    Map<String, String> extractHeaders(HttpServerExchange exchange) {
        Map<String, String> headers = new HashMap<>();

        exchange.getRequestHeaders().getHeaderNames().stream()
                .forEach(m -> headers.put(m.toString(),
                        exchange.getRequestHeaders().get(m).getFirst()
                ));

        return headers;
    }

    String extractBody(HttpServerExchange exchange) {
        StringBuilder requestBody = new StringBuilder();

        exchange.getRequestReceiver().receiveFullString((ex, data) -> {
            requestBody.append(data);
        });

        return requestBody.toString();
    }

    Map<String, String[]> extractParameters(HttpServerExchange exchange) {
        Map<String, String[]> params = new HashMap<>();

        exchange.getQueryParameters().entrySet()
                .stream()
                .forEach(m -> params.put(m.getKey(),
                        m.getValue().toArray(new String[exchange.getQueryParameters().entrySet().size()])
                ));

        return params;
    }

    String getUrl(String url) {
        String scheme = getScheme(url);

        url = url.replace(getScheme(url) + "://", "");

        String hostAndPort = url.substring(0, url.indexOf("/"));

        return scheme + "://" + hostAndPort;
    }

    String getScheme(String url) {
        return url.substring(0, url.indexOf(":"));
    }

    String getPath(String url) {
        url = url.replace(getScheme(url) + "://", "");

        if (url.indexOf("?") > 0) {
            url = url.substring(url.indexOf("/"), url.indexOf("?"));
        } else {
            url = url.substring(url.indexOf("/"));
        }

        return url;
    }


    String getQueryString(String url) {
        return url.substring(url.indexOf("?"));
    }

    String getContentType(HeaderMap headerMap) {
        String contentType = "";

        if (headerMap != null && headerMap.get("Content-Type") != null) {
            contentType = headerMap.get("Content-Type").getFirst();
        }

        return contentType;
    }

}
