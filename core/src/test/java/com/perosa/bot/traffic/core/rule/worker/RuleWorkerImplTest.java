package com.perosa.bot.traffic.core.rule.worker;

import com.perosa.bot.traffic.core.BotProxyRequest;
import com.perosa.bot.traffic.core.rule.Operator;
import com.perosa.bot.traffic.core.rule.Rule;
import com.perosa.bot.traffic.core.rule.RuleType;
import com.perosa.bot.traffic.core.service.Consumable;
import com.perosa.bot.traffic.core.service.ConsumableService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RuleWorkerImplTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleWorkerImplTest.class);

    @Test
    public void validate() {

        Assertions.assertThrows(RuntimeException.class, () -> {
            BotProxyRequest botProxyRequest = new BotProxyRequest();
            new RuleWorkerImpl(botProxyRequest).validate();
        });
    }

    @Test
    public void findEligigleTargets() {

        BotProxyRequest request = new BotProxyRequest();
        request.setBody(getJsonBody());
        RuleWorkerImpl mgr = new RuleWorkerImpl(request);


        List<Rule> rules = Arrays.asList(
                new Rule("/p1", "/from/name", "Mr X", Operator.EQUAL, RuleType.BODY,
                        Arrays.asList(
                                new ConsumableService("s1", "localhost", 8080),
                                new ConsumableService("s2", "localhost", 8081))
                ),
                new Rule("/p2", "/type", "message", Operator.NOT_EQUAL, RuleType.BODY,
                        Arrays.asList(
                                new ConsumableService("s3", "localhost", 9090),
                                new ConsumableService("s4", "localhost", 9091),
                                new ConsumableService("s5", "localhost", 9093))
                )
        );

        assertNotNull(mgr.findEligigleTargets(rules));
        Assertions.assertEquals("s1", mgr.findEligigleTargets(rules).get(0).getId());

    }

    @Test
    public void noEligigleTargets() {

        BotProxyRequest request = new BotProxyRequest();
        request.setBody(getJsonBody2());
        RuleWorkerImpl mgr = new RuleWorkerImpl(request);

        List<Rule> rules = Arrays.asList(
                new Rule("/p1", "/from/name", "Mr X", Operator.EQUAL, RuleType.BODY,
                        Arrays.asList(
                                new ConsumableService("s1", "localhost", 8080),
                                new ConsumableService("s2", "localhost", 8081))
                ),
                new Rule("/p2", "/type", "message", Operator.NOT_EQUAL, RuleType.BODY,
                        Arrays.asList(
                                new ConsumableService("s3", "localhost", 9090),
                                new ConsumableService("s4", "localhost", 9091),
                                new ConsumableService("s5", "localhost", 9093))
                )
        );

        assertNull(mgr.findEligigleTargets(rules));

    }


    @Test
    public void getPool() {

        BotProxyRequest botProxyRequest = new BotProxyRequest();

        List<Rule> rules = new RuleWorkerImpl(botProxyRequest).getPool("/webhook1");

        assertNotNull(rules);
        assertEquals(2, rules.size());
    }

    @Test
    public void getConsumable() {

        BotProxyRequest botProxyRequest = new BotProxyRequest();
        Consumable consumable = new RuleWorkerImpl(botProxyRequest).fetchFromRegistry("s00001");

        assertNotNull(consumable);
        assertEquals("ds.perosa.com", consumable.getHost());
        assertEquals(8383, consumable.getPort());
    }


    @Test
    public void prepareOutput() {

        BotProxyRequest botProxyRequest = new BotProxyRequest();
        botProxyRequest.setUrl("https://127.0.0.1/webhook/a/b?user=me");
        Consumable consumable = new RuleWorkerImpl(botProxyRequest).fetchFromRegistry("s00001");

        consumable = new RuleWorkerImpl(botProxyRequest).prepareConsumable(consumable, botProxyRequest);

        assertEquals("ds.perosa.com", consumable.getHost());
        assertEquals(8383, consumable.getPort());
        assertEquals("https://ds.perosa.com:8383/webhook/a/b?user=me", consumable.getUrl());
    }

    @Test
    public void prepareOutputPort80() {

        BotProxyRequest botProxyRequest = new BotProxyRequest();
        botProxyRequest.setUrl("https://127.0.0.1/webhook/a/b?user=me");
        Consumable consumable = new RuleWorkerImpl(botProxyRequest).fetchFromRegistry("s00002");

        consumable = new RuleWorkerImpl(botProxyRequest).prepareConsumable(consumable, botProxyRequest);

        assertEquals("ds.perosa.com", consumable.getHost());
        assertEquals(80, consumable.getPort());
        assertEquals("https://ds.perosa.com/webhook/a/b?user=me", consumable.getUrl());
    }


    private String getJsonBody() {
        return "{\n" +
                "  \"type\": \"message\",\n" +
                "  \"text\": \"ecl18c int trunk\",\n" +
                "  \"from\": {\n" +
                "    \"id\": \"default-user\",\n" +
                "    \"name\": \"Mr X\"\n" +
                "  },\n" +
                "  \"locale\": \"en-US\",\n" +
                "  \"textFormat\": \"plain\",\n" +
                "  \"timestamp\": \"2017-10-30T15:42:01.323Z\",\n" +
                "  \"channelData\": {\n" +
                "    \"clientActivityId\": \"1509376542352.5322877619614355.4\"\n" +
                "  },\n" +
                "  \"id\": \"124k29n45ck4k\",\n" +
                "  \"channelId\": \"emulator\",\n" +
                "  \"localTimestamp\": \"2017-10-30T16:42:01+01:00\",\n" +
                "  \"recipient\": {\n" +
                "    \"id\": \"i3i1lh5cdmc9\",\n" +
                "    \"name\": \"Miss Y\"\n" +
                "  },\n" +
                "  \"conversation\": {\n" +
                "    \"id\": \"k2mjibh5ch1h\"\n" +
                "  },\n" +
                "  \"serviceUrl\": \"http://localhost:60215\"\n" +
                "}";
    }

    private String getJsonBody2() {
        return "{\n" +
                "  \"type\": \"message\",\n" +
                "  \"text\": \"ecl18c int trunk\",\n" +
                "  \"from\": {\n" +
                "    \"id\": \"default-user\",\n" +
                "    \"name\": \"Mr Me\"\n" +
                "  },\n" +
                "  \"locale\": \"en-US\",\n" +
                "  \"textFormat\": \"plain\",\n" +
                "  \"timestamp\": \"2017-10-30T15:42:01.323Z\",\n" +
                "  \"channelData\": {\n" +
                "    \"clientActivityId\": \"1509376542352.5322877619614355.4\"\n" +
                "  },\n" +
                "  \"id\": \"124k29n45ck4k\",\n" +
                "  \"channelId\": \"emulator\",\n" +
                "  \"localTimestamp\": \"2017-10-30T16:42:01+01:00\",\n" +
                "  \"recipient\": {\n" +
                "    \"id\": \"i3i1lh5cdmc9\",\n" +
                "    \"name\": \"Miss You\"\n" +
                "  },\n" +
                "  \"conversation\": {\n" +
                "    \"id\": \"k2mjibh5ch1h\"\n" +
                "  },\n" +
                "  \"serviceUrl\": \"http://localhost:60215\"\n" +
                "}";
    }

}
