package com.perosa.bot.traffic.core.common;

public class UrlHelper {


    public String getScheme(String url) {
        String scheme = url.substring(0, url.indexOf(":"));

        return scheme;
    }

    public String getPath(String url) {
        url = url.replace(getScheme(url) + "://", "");

        if (url.indexOf("?") > 0) {
            url = url.substring(url.indexOf("/"), url.indexOf("?"));
        } else {
            url = url.substring(url.indexOf("/"));
        }

        return url;
    }


    public String getQueryString(String url) {
        String queryString = "";

        if (url.indexOf("?") > 0) {
            queryString = url.substring(url.indexOf("?"));
        }
        return queryString;
    }

}
