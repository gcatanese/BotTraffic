package com.perosa.bot.traffic.core.service.registry.watch;

import com.perosa.bot.traffic.core.common.EnvConfiguration;

public interface ServiceRegistryWatcher {

    static void init() {

        if(new EnvConfiguration().isThreadWatch()) {
            new ServiceRegistryThreadWatcher().startWatch();
        } else {
            new ServiceRegistryFileWatcher().startWatch();
        }
    }

    void startWatch();

}
