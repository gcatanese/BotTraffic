package com.perosa.bot.traffic.core.service.registry;

public class ServiceInstance {

    private String id;

    private String application;
    private String host;
    private int port;

    public ServiceInstance() {

    }

    public ServiceInstance(String id, String application, String host, int port) {
        this.id = id;
        this.application = application;
        this.host = host;
        this.port = port;
    }

    public ServiceInstance(String application, String host, int port) {
        this.setApplication(application);
        this.host = host;
        this.port = port;
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

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "ServiceInstance <id:" + id + " application:" + application + " host:" + host + " port:" + port + ">";
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if(obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        ServiceInstance serviceInstance = (ServiceInstance) obj;

        return serviceInstance.getId().equals(this.id);
    }
}
