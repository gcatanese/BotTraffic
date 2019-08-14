package com.perosa.bot.traffic.http.server.request;

import io.undertow.util.HeaderMap;
import io.undertow.util.HttpString;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParentRequestTest {

    @Test
    public void getUrl() {

        String url = new ParentRequest().getUrl("http://localhost/webhook/a/b?user=me");

        assertEquals("http://localhost", url);
    }

    @Test
    public void getScheme() {

        String scheme = new ParentRequest().getScheme("http://localhost/webhook/a/b?user=me");

        assertEquals("http", scheme);
    }

    @Test
    public void getSchemeFromHttpsUrl() {

        String scheme = new ParentRequest().getScheme("https://localhost/webhook/a/b?user=me");

        assertEquals("https", scheme);
    }

    @Test
    public void getPath() {

        String scheme = new ParentRequest().getPath("http://localhost/webhook/a/b?user=me");

        assertEquals("/webhook/a/b", scheme);
    }

    @Test
    public void getPathFromHttpsUrl() {

        String scheme = new ParentRequest().getPath("https://localhost/webhook/a/b?user=me");

        assertEquals("/webhook/a/b", scheme);
    }

    @Test
    public void getQueryString() {

        String scheme = new ParentRequest().getQueryString("http://localhost/webhook/a/b?user=me");

        assertEquals("?user=me", scheme);
    }

    @Test
    public void getQueryStringFromHttpsUrl() {

        String scheme = new ParentRequest().getQueryString("https://localhost/webhook/a/b?user=me");

        assertEquals("?user=me", scheme);
    }

    @Test
    public void getContentType() {

        HeaderMap headerMap = new HeaderMap();
        headerMap.add(new HttpString("Content-Type"), "plain/text");

        assertEquals("plain/text", new ParentRequest().getContentType(headerMap));
    }

    @Test
    public void getEmptyContentType() {

        HeaderMap headerMap = new HeaderMap();

        assertEquals("", new ParentRequest().getContentType(headerMap));
    }

}
