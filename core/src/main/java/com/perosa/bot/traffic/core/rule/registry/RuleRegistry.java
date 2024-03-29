package com.perosa.bot.traffic.core.rule.registry;

import com.perosa.bot.traffic.core.common.EnvConfiguration;
import com.perosa.bot.traffic.core.rule.Rule;
import com.perosa.bot.traffic.core.rule.registry.storage.RuleRegistryStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class RuleRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleRegistry.class);

    private String location;

    public RuleRegistry() {
        this.location = new EnvConfiguration().getHome() + "rules.json";
    }

    public RuleRegistry(String location) {
        this.location = location;
    }

    public List<Rule> getRules() {
        return getStorageImpl().load();
    }

    public Rule getRule(String id) {

        Optional<Rule> rule = getRules().stream()
                .filter(rule1 -> rule1.getId().equals(id))
                .findFirst();

        return rule.orElse(null);
    }

    private RuleRegistryStorage getStorageImpl() {
        return RuleRegistryStorage.make();
    }

}
