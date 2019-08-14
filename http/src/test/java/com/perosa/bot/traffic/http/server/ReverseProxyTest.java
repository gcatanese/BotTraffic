package com.perosa.bot.traffic.http.server;

import com.networknt.client.rest.LightRestClient;
import io.undertow.client.ClientResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ReverseProxyTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReverseProxyTest.class);

    @BeforeAll
    public static void beforeAll() {
        new ReverseProxy().setUp();


    }

    @Test
    public void test() throws Exception {

        String url = "http://localhost:8081";
//        boolean enableHttp2 = true;
//
//        final Http2Client client = Http2Client.getInstance();
//        final CountDownLatch latch = new CountDownLatch(10);
//        final ClientConnection connection;
//        try {
//            connection = client.connect(new URI(url), Http2Client.WORKER, Http2Client.SSL, Http2Client.BUFFER_POOL, enableHttp2 ? OptionMap.create(UndertowOptions.ENABLE_HTTP2, true): OptionMap.EMPTY).get();
//
//            LOGGER.info(connection.toString());
//
//            final ClientRequest request = new ClientRequest().setMethod(Methods.GET).setPath("/");
//            connection.sendRequest(request, );
//        } catch (Exception e) {
//            throw new ClientException(e);
//        }

        ClientResponse clientResponse = null;

        try {

            LightRestClient lightRestClient = new LightRestClient();

            Map<String, String> headerMap = new HashMap<>();
            headerMap.put("Content-Type", "application/json");
            headerMap.put("Transfer-Encoding", "chunked");

            clientResponse = lightRestClient.get(url, "svc1",
                    ClientResponse.class, headerMap);



        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }


}
