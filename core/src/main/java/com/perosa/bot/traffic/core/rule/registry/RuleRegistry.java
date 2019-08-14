package com.perosa.bot.traffic.core.rule.registry;

import com.perosa.bot.traffic.core.common.CoreConfiguration;
import com.perosa.bot.traffic.core.rule.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class RuleRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleRegistry.class);

    private static List<Rule> _rules = null;

    public List<Rule> getRules() {
        if(_rules == null) {
            _rules = new RuleRegistryLoader().load();
        }

        return _rules;
    }

    public Rule getRule(String id) {

        Optional<Rule> rule = getRules().stream()
                .filter(rule1 -> rule1.getId().equals(id))
                .findFirst();

        return rule.orElse(null);
    }

    public static String getLocation() {
        return new CoreConfiguration().getHome() + "rules.json";
    }

    public void setRules(List<Rule> rules) {
        _rules = rules;
    }

}
