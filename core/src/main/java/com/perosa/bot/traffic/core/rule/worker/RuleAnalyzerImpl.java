package com.perosa.bot.traffic.core.rule.worker;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.perosa.bot.traffic.core.BotProxyRequest;
import com.perosa.bot.traffic.core.rule.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class RuleAnalyzerImpl implements RuleAnalyzer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleAnalyzerImpl.class);

    private BotProxyRequest request;

    public RuleAnalyzerImpl() {
    }

    public RuleAnalyzerImpl(BotProxyRequest request) {
        this.request = request;
    }

    @Override
    public String findValue(Rule rule) {
        String ret = "";

        if (rule.getType() != null && rule.getExpression() != null) {
            if (rule.getType().isBodyRule()) {
                ret = findValueInBody(rule.getExpression());
            } else if (rule.getType().isParameterRule()) {
                ret = findValueInParameters(rule.getExpression());
            } else if (rule.getType().isHeaderRule()) {
                ret = findValueInHeaders(rule.getExpression());
            }
        }

        return ret;

    }

    @Override
    public String findValueInBody(String elementPath) {
        String ret = "";

        if (this.request.getBody() != null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(this.request.getBody());

                if (jsonNode != null) {
                    jsonNode = jsonNode.at(elementPath);

                    if (jsonNode != null) {
                        ret = jsonNode.asText();
                    }
                }
            } catch (IOException io) {
                io.printStackTrace();
            }
        }

        return ret;
    }

    @Override
    public String findValueInParameters(String parameter) {
        String ret = "";

        if (this.request.getParameters() != null) {

            String[] values = this.request.getParameters().get(parameter);

            if (values != null) {
                ret = values[0];
            }
        }
        return ret;
    }


    @Override
    public String findValueInHeaders(String parameter) {
        String ret = "";

        if (this.request.getHeaders() != null) {

            String value = this.request.getHeaders().get(parameter);

            if (value != null) {
                ret = value;
            }

            return ret;
        }

        return ret;

    }

}
