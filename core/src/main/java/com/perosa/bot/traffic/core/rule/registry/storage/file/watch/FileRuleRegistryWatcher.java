package com.perosa.bot.traffic.core.rule.registry.storage.file.watch;

import com.perosa.bot.traffic.core.common.EnvConfiguration;

public interface FileRuleRegistryWatcher {

    static void init() {

        if(new EnvConfiguration().isThreadWatch()) {
            new FileRuleRegistryThreadWatcher().startWatch();
        } else {
            new FileRuleRegistryFileWatcher().startWatch();
        }
    }

    void startWatch();

}
