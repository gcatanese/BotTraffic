package com.perosa.bot.traffic.core.rule.worker;

import com.perosa.bot.traffic.core.BotProxyRequest;
import com.perosa.bot.traffic.core.common.EnvConfiguration;
import com.perosa.bot.traffic.core.common.UrlHelper;
import com.perosa.bot.traffic.core.rule.Rule;
import com.perosa.bot.traffic.core.rule.RuleStatus;
import com.perosa.bot.traffic.core.rule.registry.RuleRegistry;
import com.perosa.bot.traffic.core.service.Consumable;
import com.perosa.bot.traffic.core.service.ConsumableService;
import com.perosa.bot.traffic.core.service.TargetUrl;
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

    private RuleAnalyzer ruleAnalyzer;
    private UrlHelper urlHelper;

    private Strategy strategy;

    public RuleWorkerImpl() {
        this.strategy = new SingleServiceStrategy();
        this.urlHelper = new UrlHelper();
    }

    @Override
    public Consumable process(BotProxyRequest request) {

        this.ruleAnalyzer = new RuleAnalyzer(request);

        Consumable consumable = null;

        validate(request);

        List<Rule> rules = getPool(getRequestedPath(request));

        if (rules != null && !rules.isEmpty()) {

            Rule rule = findWinningRule(rules);
            LOGGER.debug("Winning rule " + rule);

            if (rule != null && rule.getTargetServices() != null && !rule.getTargetServices().isEmpty()) {
                // fetch destination service

                consumable = getStrategy().getTarget(rule.getTargetServices());

                consumable = fetchFromRegistry(consumable.getId());

                consumable = prepareConsumable(consumable, rule, request);

                LOGGER.info("target->" + consumable.getUrl());
            } else if (rule != null && rule.getTargetUrls() != null && !rule.getTargetUrls().isEmpty()) {
                // fetch destination url

                consumable = prepareConsumable(rule.getTargetUrls().get(0), rule);

                LOGGER.info("target->" + consumable.getUrl());
            }
        }

        if (consumable == null) {
            LOGGER.warn("No matching rule for path " + getRequestedPath(request));
            if(new EnvConfiguration().isFallbackRequestedUrl()) {
                consumable = prepareConsumableWithRequestedUrl(request);
            }
        }

        return consumable;
    }

    List<Rule> getPool(String path) {

        List<Rule> rules = new RuleRegistry().getRules().stream()
                .filter(r -> path.startsWith(r.getPath()))
                .collect(Collectors.toList());

        return rules;
    }

    Consumable prepareConsumable(Consumable consumable, Rule rule, BotProxyRequest request) {

        ConsumableService consumableService = (ConsumableService) consumable;

        if(consumable.getPort() == 80) {
            consumableService.setUrl("http://" + consumable.getHost()
                    + getRequestedPath(request) + getRequestedQueryString(request));
        } else if (consumable.getPort() == 443) {
            consumableService.setUrl("https://" + consumable.getHost()
                    + getRequestedPath(request) + getRequestedQueryString(request));
        } else {
            consumableService.setUrl("http://" + consumable.getHost() + ":" + consumable.getPort()
                    + getRequestedPath(request) + getRequestedQueryString(request));
        }

        consumableService.setWorkflow(rule.getWorkflow());

        return consumableService;
    }

    Consumable prepareConsumable(TargetUrl targetUrl, Rule rule) {

        ConsumableService consumableService = new ConsumableService("na", targetUrl.getUrl());

        consumableService.setWorkflow(rule.getWorkflow());

        return consumableService;
    }

    Consumable prepareConsumableWithRequestedUrl(BotProxyRequest request) {
        ConsumableService consumableService = new ConsumableService();
        consumableService.setUrl(request.getUrl());

        LOGGER.info("fwdTo " + consumableService.getUrl());

        return consumableService;
    }

    void validate(BotProxyRequest request) {

        if (request.getUrl() == null) {
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
                .filter(r -> r.isCatchAll() || r.getOperator().apply(getRuleAnalyzer().findValue(r), r.getValue()))
                .findFirst();

        if (rule.isPresent()) {
            ret = rule.get();
        }

        return ret;
    }

    private String getRequestedPath(BotProxyRequest request) {
        return getUrlHelper().getPath(request.getUrl());
    }

    private String getRequestedQueryString(BotProxyRequest request) {
        return getUrlHelper().getQueryString(request.getUrl());
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
