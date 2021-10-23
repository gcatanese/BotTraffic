package com.perosa.bot.traffic.core.rule.worker;

import com.perosa.bot.traffic.core.BotProxyRequest;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RuleAnalyzerTest {

    @Test
    public void findValueInBody() {

        String elementPath = "/country";

        BotProxyRequest request = new BotProxyRequest();
        request.setBody(getJsonBody());

        RuleAnalyzerImpl ruleAnalyzer = new RuleAnalyzerImpl(request);
        assertEquals("Italy", ruleAnalyzer.findValueInBody(elementPath));

    }

    @Test
    public void findValueInHeaders() {

        String elementPath = "username1";

        BotProxyRequest request = new BotProxyRequest();
        request.setHeaders(Collections.singletonMap("username1", "password1"));

        RuleAnalyzerImpl ruleAnalyzer = new RuleAnalyzerImpl(request);
        assertEquals("password1", ruleAnalyzer.findValueInHeaders(elementPath));

    }

    @Test
    public void findValueWhenNoHeaders() {

        String elementPath = "username1";

        BotProxyRequest request = new BotProxyRequest();

        RuleAnalyzerImpl ruleAnalyzer = new RuleAnalyzerImpl(request);
        assertEquals("", ruleAnalyzer.findValueInHeaders(elementPath));

    }

    @Test
    public void findValueInParameters() {

        String elementPath = "list";

        BotProxyRequest request = new BotProxyRequest();
        request.setParameters(Collections.singletonMap("list", new String[]{"val1", "val2"}));

        RuleAnalyzerImpl ruleAnalyzer = new RuleAnalyzerImpl(request);
        assertEquals("val1", ruleAnalyzer.findValueInParameters(elementPath));

    }

    private String getJsonBody() {
        return "{\n" +
                "  \"type\": \"message\",\n" +
                "  \"text\": \"ecl18c int trunk\",\n" +
                "  \"from\": {\n" +
                "    \"id\": \"default-user\",\n" +
                "    \"name\": \"Mr X\"\n" +
                "  },\n" +
                "  \"country\": \"Italy\",\n" +
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
}
