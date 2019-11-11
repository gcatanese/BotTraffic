package com.perosa.bot.traffic.http.server.dispatch.workflow;

import com.perosa.bot.traffic.http.client.Forwarder;
import com.perosa.bot.traffic.http.client.ForwarderResponse;
import com.perosa.bot.traffic.http.client.wrap.Get;
import com.perosa.bot.traffic.http.client.wrap.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class Shadower {

    private static final Logger LOGGER = LoggerFactory.getLogger(Shadower.class);

    ExecutorService executor = Executors.newFixedThreadPool(5);

    private Forwarder forwarder;

    public Shadower(Forwarder forwarder) {
        this.forwarder = forwarder;
    }

    public Future<String> get(Get input) throws Exception {
        return push(callableGet(input));
    }

    public Future<String> post(Post input) throws Exception {
        return push(callablePost(input));
    }

    Future<String> push(Callable<String> task) {
        return executor.submit(task);
    }

    Callable<String> callableGet(Get input) {

        Callable<String> callable = () -> {
            ForwarderResponse forwarderResponse = getForwarder().get(input);
            return "Pushed Get";
        };

        return callable;
    }

    Callable<String> callablePost(Post input) {

        Callable<String> callable = () -> {
            ForwarderResponse forwarderResponse = getForwarder().post(input);
            return "Pushed Post";
        };

        return callable;
    }

    public Forwarder getForwarder() {
        return forwarder;
    }
}
