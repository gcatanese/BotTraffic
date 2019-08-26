package com.perosa.bot.traffic.core.service.registry.watch;

import com.perosa.bot.traffic.core.common.CoreConfiguration;

public interface ServiceRegistryWatcher {

    static void init() {

        if(new CoreConfiguration().isThreadWatch()) {
            new ServiceRegistryThreadWatcher().startWatch();
        } else {
            new ServiceRegistryFileWatcher().startWatch();
        }
    }

    void startWatch();

}
