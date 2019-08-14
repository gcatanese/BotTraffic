package com.perosa.bot.traffic.core.service.registry;

import com.perosa.bot.traffic.core.common.CoreConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ServiceRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRegistry.class);

    private static List<ServiceInstance> _serviceInstances = null;

    public ServiceRegistry() {
    }

    public List<ServiceInstance> getServiceInstances() {
        if(_serviceInstances == null) {
            _serviceInstances = new ServiceRegistryLoader().load();
        }

        return _serviceInstances;
    }

    public static String getLocation() {
        return new CoreConfiguration().getHome() + "services.json";
    }

    public void setServiceInstances(List<ServiceInstance> serviceInstances) {
        _serviceInstances = serviceInstances;
    }


}
