package com.perosa.bot.traffic.core.rule.registry.storage;

import com.perosa.bot.traffic.core.rule.Rule;
import com.perosa.bot.traffic.core.rule.registry.storage.file.FileRuleRegistry;

import java.util.List;

public interface RuleRegistryStorage {

    List<Rule> load();

    static RuleRegistryStorage make() {
        return new FileRuleRegistry();
    }
}
