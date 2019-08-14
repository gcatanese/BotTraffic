package com.perosa.bot.traffic.core.service.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.perosa.bot.traffic.core.service.Consumable;
import com.perosa.bot.traffic.core.service.ConsumableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class ServiceRegistryLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRegistryLoader.class);


    public List<ServiceInstance> load() {
        List<ServiceInstance> serviceInstances = new ArrayList<>();

        try {
            serviceInstances = unmarshal(getJson(getFilePath()));

            LOGGER.info("Available services: " + serviceInstances);

        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return serviceInstances;

    }

    public String getFilePath() throws IOException {
        return ServiceRegistry.getLocation();
    }

    List<ServiceInstance> unmarshal(String json) throws IOException {
        List<ServiceInstance> serviceInstance = null;

        ObjectMapper objectMapper = new ObjectMapper();

        serviceInstance = objectMapper
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .findAndRegisterModules()
                .readValue(json, new TypeReference<List<ServiceInstance>>() { });

        return serviceInstance;
    }

    String getJson(String filepath) throws IOException {
        String json = "";

        File file = new File(filepath);

        if (!file.exists()) {
            throw new RuntimeException("File not found: " + filepath);
        } else {
            byte[] b = Files.readAllBytes(file.toPath());
            json = new String(b);
        }

        return json;
    }

    Map<String, Consumable> loadAsMap(List<ServiceInstance> serviceInstances) {

        Map<String, Consumable> map = new HashMap<>();

        if (serviceInstances != null) {
            serviceInstances.stream()
                    .forEach(s -> {
                        Consumable consumable = convert(s);
                        map.put(consumable.getId(), consumable);
                    });
        }

        return map;
    }

    public Consumable convert(ServiceInstance serviceInstance) {
        ConsumableService consumableService = new ConsumableService();

        consumableService.setId(getId(serviceInstance));
        consumableService.setHost(serviceInstance.getHost());
        consumableService.setPort(serviceInstance.getPort());

        return consumableService;
    }

    String getId(ServiceInstance serviceInstance) {
        String id = null;

        if (serviceInstance.getId() != null) {
            id = serviceInstance.getId();
        } else {
            id = UUID.randomUUID().toString();
        }

        return id;
    }

}
