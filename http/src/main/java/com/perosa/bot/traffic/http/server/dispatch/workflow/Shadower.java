package com.perosa.bot.traffic.http.server.dispatch.workflow;

import com.perosa.bot.traffic.http.client.Forwarder;
import com.perosa.bot.traffic.http.client.ForwarderResponse;
import com.perosa.bot.traffic.http.client.JavaClientImpl;
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

    public void get(Get input) throws Exception {
        push(callableGet(input));
    }

    public void post(Post input) throws Exception {
        push(callablePost(input));
    }

    void push(Callable<String> task) {
        Future<String> future = executor.submit(task);
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
