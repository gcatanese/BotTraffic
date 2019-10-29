package com.perosa.bot.traffic.core.service;

public class ConsumableService implements Consumable {

    private String id;
    private String host;
    private int port;
    private String url;
    private int weight = 100;

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

    public void setWeight(int weight) {
        this.weight = weight;
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
