package com.perosa.bot.traffic.core.rule.worker;

import com.perosa.bot.traffic.core.BotProxyRequest;
import com.perosa.bot.traffic.core.common.UrlHelper;
import com.perosa.bot.traffic.core.rule.Rule;
import com.perosa.bot.traffic.core.rule.RuleStatus;
import com.perosa.bot.traffic.core.rule.registry.RuleRegistry;
import com.perosa.bot.traffic.core.service.Consumable;
import com.perosa.bot.traffic.core.service.ConsumableService;
import com.perosa.bot.traffic.core.service.registry.ServiceInstance;
import com.perosa.bot.traffic.core.service.registry.ServiceRegistry;
import com.perosa.bot.traffic.core.service.registry.ServiceRegistryLoader;
import com.perosa.bot.traffic.core.strategy.SingleServiceStrategy;
import com.perosa.bot.traffic.core.strategy.Strategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RuleWorkerImpl implements RuleWorker {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleWorkerImpl.class);

    private BotProxyRequest request;
    private RuleAnalyzer ruleAnalyzer;
    private UrlHelper urlHelper;

    private Strategy strategy;

    public RuleWorkerImpl(BotProxyRequest request) {
        this.strategy = new SingleServiceStrategy();
        this.urlHelper = new UrlHelper();
        this.request = request;
        this.ruleAnalyzer = new RuleAnalyzer(request);
    }

    @Override
    public Consumable process() {

        Consumable consumable = null;

        validate();

        List<Rule> rules = getPool(getRequestedPath());

        if (rules != null && !rules.isEmpty()) {
            Rule rule = findWinningRule(rules);

            if (rule != null && rule.getTargets() != null) {

                consumable = getStrategy().getTarget(rule.getTargets());

                consumable = fetchFromRegistry(consumable.getId());

                consumable = prepareConsumable(consumable, rule);

                LOGGER.info("fwdTo " + consumable.getUrl());
            }
        }

        if (consumable == null) {
            LOGGER.warn("No matching rule for path " + getRequestedPath());
            consumable = prepareConsumableWithRequestedUrl(getRequest());
        }

        return consumable;
    }

    List<Rule> getPool(String path) {

        List<Rule> rules = new RuleRegistry().getRules().stream()
                .filter(r -> path.startsWith(r.getPath()))
                .collect(Collectors.toList());

        return rules;
    }

    Consumable prepareConsumable(Consumable consumable, Rule rule) {

        ConsumableService consumableService = (ConsumableService) consumable;

        if(consumable.getPort() == 80) {
            consumableService.setUrl("http://" + consumable.getHost()
                    + getRequestedPath() + getRequestedQueryString());
        } else if (consumable.getPort() == 443) {
            consumableService.setUrl("https://" + consumable.getHost()
                    + getRequestedPath() + getRequestedQueryString());
        } else {
            consumableService.setUrl("http://" + consumable.getHost() + ":" + consumable.getPort()
                    + getRequestedPath() + getRequestedQueryString());
        }

        consumableService.setWorkflow(rule.getWorkflow());

        return consumableService;
    }

    Consumable prepareConsumableWithRequestedUrl(BotProxyRequest request) {
        ConsumableService consumableService = new ConsumableService();
        consumableService.setUrl(request.getUrl());

        LOGGER.info("fwdTo " + consumableService.getUrl());

        return consumableService;
    }

    void validate() {

        if (this.request.getUrl() == null) {
            throw new RuntimeException("Url is undefined");
        }


    }

    Consumable fetchFromRegistry(String id) {
        Optional<ServiceInstance> o = new ServiceRegistry().getServiceInstances().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();

        if (o.isPresent()) {
            return new ServiceRegistryLoader().convert(o.get());
        } else {
            return null;
        }
    }

    Rule findWinningRule(List<Rule> rules) {
        Rule ret = null;

        Optional<Rule> rule = rules.stream()
                .filter(r -> r.getStatus().equals(RuleStatus.ACTIVE))
                .filter(r -> r.getOperator().apply(getRuleAnalyzer().findValue(r), r.getValue()))
                .findFirst();

        if (rule.isPresent()) {
            ret = rule.get();
        }

        return ret;
    }

    private String getRequestedPath() {
        return getUrlHelper().getPath(getRequest().getUrl());
    }

    private String getRequestedQueryString() {
        return getUrlHelper().getQueryString(getRequest().getUrl());
    }

    public BotProxyRequest getRequest() {
        return request;
    }

    public void setRequest(BotProxyRequest request) {
        this.request = request;
    }

    public RuleAnalyzer getRuleAnalyzer() {
        return ruleAnalyzer;
    }

    public void setRuleAnalyzer(RuleAnalyzer ruleAnalyzer) {
        this.ruleAnalyzer = ruleAnalyzer;
    }

    public UrlHelper getUrlHelper() {
        return urlHelper;
    }

    public void setUrlHelper(UrlHelper urlHelper) {
        this.urlHelper = urlHelper;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

}
