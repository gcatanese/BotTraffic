package com.perosa.bot.traffic.core.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UrlHelperTest {

    @Test
    public void getScheme() {

        String scheme = new UrlHelper().getScheme("http://localhost/webhook/a/b?user=me");

        assertEquals("http", scheme);
    }

    @Test
    public void getSchemeFromHttpsUrl() {

        String scheme = new UrlHelper().getScheme("https://localhost/webhook/a/b?user=me");

        assertEquals("https", scheme);
    }

    @Test
    public void getPath() {

        String path = new UrlHelper().getPath("http://localhost/webhook/a/b?user=me");

        assertEquals("/webhook/a/b", path);
    }

    @Test
    public void getPathFromHttpsUrl() {

        String path = new UrlHelper().getPath("https://localhost/webhook/a/b?user=me");

        assertEquals("/webhook/a/b", path);
    }

    @Test
    public void getPathWithoutQueryString() {

        String path = new UrlHelper().getPath("http://localhost/webhook/a/b");

        assertEquals("/webhook/a/b", path);
    }

    @Test
    public void getQueryString() {

        String queryString = new UrlHelper().getQueryString("http://localhost/webhook/a/b?user=me");

        assertEquals("?user=me", queryString);
    }

    @Test
    public void getEmptyQueryString() {

        String queryString = new UrlHelper().getQueryString("http://localhost/webhook/a/b");

        assertEquals("", queryString);
    }

    @Test
    public void getQueryStringFromHttpsUrl() {

        String queryString = new UrlHelper().getQueryString("https://localhost/webhook/a/b?user=me");

        assertEquals("?user=me", queryString);
    }

}
