package com.perosa.bot.traffic.http.server;

import com.networknt.client.rest.LightRestClient;
import io.undertow.client.ClientResponse;
import io.undertow.server.HttpServerExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ReverseProxyTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReverseProxyTest.class);

    private ReverseProxy reverseProxy;
    private LightRestClient lightRestClient;

    @Mock
    HttpServerExchange exchange;
    @Mock
    private Dispatcher dispatcher;

    @BeforeEach
    private void init() {
        lightRestClient = new LightRestClient();

        dispatcher = Mockito.mock(Dispatcher.class);
        reverseProxy = new ReverseProxy(dispatcher);

        reverseProxy.setUp();
    }

    @Test
    void get() throws Exception {

        ClientResponse clientResponse = lightRestClient.get("http://localhost:8886", "/svc1", ClientResponse.class, new HashMap<String, String>());
        assertEquals(200, clientResponse.getResponseCode());
    }

    @Test
    void getPort() {
        assertEquals(8886, reverseProxy.getPort());
    }


}
