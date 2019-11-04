package com.perosa.bot.traffic.core.service;

import com.perosa.bot.traffic.core.rule.RuleWorkflow;

public class ConsumableService implements Consumable {

    private String id;
    private String host;
    private int port;
    private String url;
    private int weight = 100;
    private RuleWorkflow workflow = RuleWorkflow.ROUTE;

    public ConsumableService() {
    }

    public ConsumableService(String id) {
        this.id = id;
    }

    public ConsumableService(String id, String host, int port) {
        this.id = id;
        this.host = host;
        this.port = port;
    }

    public ConsumableService(String id, String host, int port, int weight) {
        this.id = id;
        this.host = host;
        this.port = port;
        this.weight = weight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public boolean isRouting() {
        return RuleWorkflow.ROUTE.equals(getWorkflow());
    }

    @Override
    public boolean isFiltering() {
        return RuleWorkflow.FILTER.equals(getWorkflow());
    }

    @Override
    public boolean isShadowing() {
        return RuleWorkflow.SHADOW.equals(getWorkflow());
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public RuleWorkflow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(RuleWorkflow workflow) {
        this.workflow = workflow;
    }

    @Override
    public String toString() {
        return "ConsumableService[" +
                "id:" + id +
                ", host:" + host +
                ", port:" + port +
                "]";
    }


}
