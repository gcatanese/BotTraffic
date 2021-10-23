package com.perosa.bot.traffic.http.server.dispatch.request;

import com.perosa.bot.traffic.core.BotProxyRequest;
import com.perosa.bot.traffic.core.rule.RuleWorkflow;
import com.perosa.bot.traffic.core.rule.worker.RuleAnalyzerImpl;
import com.perosa.bot.traffic.core.rule.worker.RuleWorker;
import com.perosa.bot.traffic.core.rule.worker.RuleWorkerImpl;
import com.perosa.bot.traffic.core.service.Consumable;
import com.perosa.bot.traffic.core.service.ConsumableService;
import com.perosa.bot.traffic.http.client.Forwarder;
import com.perosa.bot.traffic.http.client.ForwarderResponse;
import com.perosa.bot.traffic.http.client.wrap.Post;
import com.perosa.bot.traffic.http.server.dispatch.workflow.Router;
import com.perosa.bot.traffic.http.server.dispatch.workflow.Shadower;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HeaderMap;
import io.undertow.util.HttpString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostRequestTest {

    @Mock
    HttpServerExchange exchange;

    @Mock
    Forwarder forwarderMock;

    @Mock
    RuleWorker ruleWorkerMock;

    @Mock
    Router routerMock;

    @Mock
    Shadower shadowerMock;

    @Test
    void initBotProxyRequest() {

        when(exchange.getRequestURL()).thenReturn("http://localhost");

        HeaderMap headerMap = new HeaderMap();
        headerMap.add(new HttpString("host"), "localhost");
        headerMap.add(new HttpString("authorization"), "bldfsdf$%45");
        when(exchange.getRequestHeaders()).thenReturn(headerMap);

        BotProxyRequest request = new PostRequest().initBotProxyRequest(exchange);

        assertNotNull(request);
        assertEquals("http://localhost", request.getUrl());
        assertEquals("", request.getBody()); // undef body during unit test
    }

    @Test
    void initPost() {

        BotProxyRequest request = new BotProxyRequest();
        request.setBody("body");
        RuleWorkerImpl mgr = new RuleWorkerImpl();
        mgr.setRuleAnalyzer(new RuleAnalyzerImpl(request));


        Post post = new PostRequest().initPost(getRoutingConsumable(), request);

        assertNotNull(post);
        assertEquals("http://localhost:9090", post.getUrl());
        assertEquals("/path", post.getPath());
    }

    @Test
    void handle() throws Exception {

        when(exchange.getRequestURL()).thenReturn("http://localhost/svc1");

        HeaderMap headerMap = new HeaderMap();
        headerMap.add(new HttpString("host"), "localhost");
        headerMap.add(new HttpString("authorization"), "bldfsdf$%45");
        when(exchange.getRequestHeaders()).thenReturn(headerMap);

        when(ruleWorkerMock.process(isA(BotProxyRequest.class))).thenReturn(getRoutingConsumable());

        when(routerMock.post(isA(Post.class))).thenReturn(new ForwarderResponse());

        PostRequest post = new PostRequest();
        post.setRouter(routerMock);
        post.setShadower(shadowerMock);
        post.setForwarder(forwarderMock);
        post.setRuleWorker(ruleWorkerMock);

        post.handle(exchange);

        verify(routerMock, times(1)).post(isA(Post.class));
        verify(shadowerMock, times(0)).post(isA(Post.class));
        verify(ruleWorkerMock, times(1)).process(isA(BotProxyRequest.class));

    }

    @Test
    void handleWithShadowing() throws Exception {

        when(exchange.getRequestURL()).thenReturn("http://localhost/svc1");

        HeaderMap headerMap = new HeaderMap();
        headerMap.add(new HttpString("host"), "localhost");
        headerMap.add(new HttpString("authorization"), "bldfsdf$%45");
        when(exchange.getRequestHeaders()).thenReturn(headerMap);

        when(ruleWorkerMock.process(isA(BotProxyRequest.class))).thenReturn(getShadowingConsumable());

        PostRequest post = new PostRequest();
        post.setRouter(routerMock);
        post.setShadower(shadowerMock);
        post.setForwarder(forwarderMock);
        post.setRuleWorker(ruleWorkerMock);

        post.handle(exchange);

        verify(routerMock, times(0)).post(isA(Post.class));
        verify(shadowerMock, times(1)).post(isA(Post.class));
        verify(ruleWorkerMock, times(1)).process(isA(BotProxyRequest.class));

    }

    private Consumable getRoutingConsumable() {
        ConsumableService consumable = new ConsumableService("01", "localhost", 9090);
        consumable.setUrl("http://localhost:9090/path");
        consumable.setWorkflow(RuleWorkflow.ROUTE);

        return consumable;
    }

    private Consumable getShadowingConsumable() {
        ConsumableService consumable = new ConsumableService("01", "localhost", 9090);
        consumable.setUrl("http://localhost:9090/path");
        consumable.setWorkflow(RuleWorkflow.SHADOW);

        return consumable;
    }

}