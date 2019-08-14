package com.perosa.bot.traffic.core.service.registry;

import com.perosa.bot.traffic.core.service.Consumable;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ServiceRegistryLoaderTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRegistryLoaderTest.class);

    @Test
    void getJson() throws IOException {

        String json = new ServiceRegistryLoader().getJson("src/test/resources/services.json");

        LOGGER.info(json);

        assertNotNull(json);

    }

    @Test
    void unmarshal() throws IOException {

        String json = "" +
                "[" +
                "{" +
                "\"application\":\"app1\", " +
                "\"host\":\"localhost\", " +
                "\"port\":\"8888\"" +
                "}, " +
                "{" +
                "\"application\":\"app1\", " +
                "\"host\":\"localhost\", " +
                "\"port\":\"8888\"" +
                "} " +
                "]";

        List<ServiceInstance> serviceInstances = new ServiceRegistryLoader().unmarshal(json);

        assertNotNull(serviceInstances);
        assertEquals(2, serviceInstances.size());

    }

    public void convert() {
        ServiceInstance serviceInstance = new ServiceInstance("01", "app1", "localhost", 8080);

        Consumable consumable = new ServiceRegistryLoader().convert(serviceInstance);
        assertNotNull(consumable);
        assertEquals("01", consumable.getId());

    }

    @Test
    public void getId() {
        ServiceInstance serviceInstance = new ServiceInstance("01", "app1", "localhost", 8080);
        assertEquals("01", new ServiceRegistryLoader().getId(serviceInstance));
    }

    @Test
    public void getGeneratedId() {
        ServiceInstance serviceInstance = new ServiceInstance("app1", "localhost", 8080);
        assertEquals(36, new ServiceRegistryLoader().getId(serviceInstance).length());
    }

    @Test
    public void loadAsMap() {
        List<ServiceInstance> serviceInstances = new ArrayList<>();
        serviceInstances.add(new ServiceInstance("01", "app1", "localhost", 8080));
        serviceInstances.add(new ServiceInstance("02", "app1", "localhost", 8081));

        Map<String, Consumable> map = new ServiceRegistryLoader().loadAsMap(serviceInstances);

        assertNotNull(map);
        assertNotNull(map.get("01"));
        assertEquals("localhost", map.get("01").getHost());
    }


}
