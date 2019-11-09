package com.perosa.bot.traffic.http.server.dispatch;

import com.perosa.bot.traffic.http.server.dispatch.request.GetRequest;
import com.perosa.bot.traffic.http.server.dispatch.request.PostRequest;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class DispatcherTest {

    @Mock
    HttpServerExchange exchange;

    @Test
    void getRequestHandlerForGet() {

        when(exchange.getRequestMethod()).thenReturn(new HttpString("GET"));

        Dispatcher dispatcher = new Dispatcher();

        assertTrue(dispatcher.getRequestHandler(exchange) instanceof GetRequest);
    }

    @Test
    void getRequestHandlerForPost() {

        when(exchange.getRequestMethod()).thenReturn(new HttpString("POST"));

        Dispatcher dispatcher = new Dispatcher();

        assertTrue(dispatcher.getRequestHandler(exchange) instanceof PostRequest);
    }

    @Test
    void getRequestHandlerForPut() {

        when(exchange.getRequestMethod()).thenReturn(new HttpString("PUT"));

        Dispatcher dispatcher = new Dispatcher();

        assertNull(dispatcher.getRequestHandler(exchange));
    }


}