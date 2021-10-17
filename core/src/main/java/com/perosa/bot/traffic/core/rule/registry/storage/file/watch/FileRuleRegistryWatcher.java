package com.perosa.bot.traffic.core.rule.registry.storage.file.watch;

import com.perosa.bot.traffic.core.common.EnvConfiguration;

public interface FileRuleRegistryWatcher {

    static void init(String location) {

        if(new EnvConfiguration().isThreadWatch()) {
            new FileRuleRegistryThreadWatcher(location).startWatch();
        } else {
            new FileRuleRegistryFileWatcher(location).startWatch();
        }
    }

    void startWatch();

}
