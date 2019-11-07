package com.perosa.bot.traffic.http.client;

import com.perosa.bot.traffic.http.client.wrap.Get;
import com.perosa.bot.traffic.http.client.wrap.Post;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

public class LightRestClientImplTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(LightRestClientImplTest.class);

    private static Undertow server = null;

    @BeforeAll
    static void start() {
        buildAndStartServer(8899, "localhost");
    }

    @AfterAll
    static void stop() {
        server.stop();
    }

    @Test
    void get() throws Exception {

        LightRestClientImpl client = new LightRestClientImpl();

        Get input = new Get("http://localhost:8899", "/");
        ForwarderResponse response = client.get(input);

        assertNotNull(response);
        assertEquals(200, response.getResponseCode());
    }

    //@Test TODO (POST call returns null)
    void post() throws Exception {

        LightRestClientImpl client = new LightRestClientImpl();

        Post input = new Post("http://localhost:8899", "/");
        input.setBody("payload");

        ForwarderResponse response = client.post(input);

        assertNotNull(response);
        assertEquals(200, response.getResponseCode());
    }


    private static void buildAndStartServer(int port, String host) {
        server = Undertow.builder()
                .addHttpListener(port, host)
                .setHandler(new HttpHandler() {
                    @Override
                    public void handleRequest(HttpServerExchange exchange) throws Exception {
                        LOGGER.info("Ok");
                        exchange.setStatusCode(200);
                        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                        exchange.getResponseSender().send("Ok");
                    }
                })
                .build();
        server.start();
    }
}