package com.perosa.bot.traffic.http.metrics.prometheus;

import com.perosa.bot.traffic.core.common.UrlHelper;
import io.prometheus.client.Counter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Counters {

    private static final Logger LOGGER = LoggerFactory.getLogger(Counters.class);

    static Map<String, Counter> counters = new HashMap<>();

    static Counter requests = Counter.build()
                     .name("requests_total").help("Total requests.")
          .labelNames("method").register();

    public void increaseSuccess(String url) {
        increase(getName(url) + "_success");
    }

    public void increaseError(String url) {
        increase(getName(url) + "_failure");
    }

    String getName(String url) {
        String name = url.replace(new UrlHelper().getScheme(url) + "://", "");
        name = name.replace("/", "_");

        return name;
    }

    void increase(String name) {

        LOGGER.info("increaseCounter " + name);

//        Counter counter = counters.get(name);
//
//        if(counter == null) {
//            counter = Counter.build()
//                    .name(name)
//                    .help(name).register();
//        }

        //counter.inc();

        requests.inc();
    }
}
