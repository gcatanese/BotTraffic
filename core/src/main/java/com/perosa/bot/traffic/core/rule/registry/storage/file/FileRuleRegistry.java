package com.perosa.bot.traffic.core.rule.registry.storage.file;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.perosa.bot.traffic.core.common.EnvConfiguration;
import com.perosa.bot.traffic.core.rule.Rule;
import com.perosa.bot.traffic.core.rule.registry.storage.RuleRegistryStorage;
import com.perosa.bot.traffic.core.rule.registry.storage.file.watch.FileRuleRegistryWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileRuleRegistry implements RuleRegistryStorage {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileRuleRegistry.class);

    private static List<Rule> rules = null;

    public FileRuleRegistry() {
        FileRuleRegistryWatcher.init();
    }

    @Override
    public List<Rule> load() {

        if (rules == null) {
            try {
                rules = unmarshal(getJson(getLocation()));

                LOGGER.info("Available rules: " + rules);

            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }

        return rules;
    }

    @Override
    public void save(String json) {

        try {
            String filename = getLocation();

            Path path = Paths.get(filename);
            byte[] strToBytes = json.getBytes();

            Files.write(path, strToBytes, StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setRules(List<Rule> rules) {
        FileRuleRegistry.rules = rules;
    }

    Map<String, List<Rule>> loadAsMap(List<Rule> rules) {

        Map<String, List<Rule>> map = new HashMap<>();

        if (rules != null) {
            rules.stream()
                    .forEach(rule -> {
                        List<Rule> queue = map.get(rule.getPath());
                        if (queue == null) {
                            queue = new ArrayList<>();
                        }
                        queue.add(rule);

                        map.put(rule.getPath(), queue);
                    });
        }

        return map;
    }


    List<Rule> unmarshal(String json) throws IOException {
        List<Rule> rules = null;

        ObjectMapper objectMapper = new ObjectMapper();

        rules = objectMapper
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .findAndRegisterModules()
                .readValue(json, new TypeReference<List<Rule>>() {
                });

        return rules;
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

    String getLocation() {
        return new EnvConfiguration().getHome() + "rules.json";
    }


}
