package com.perosa.bot.traffic.http.client;

import com.perosa.bot.traffic.http.client.wrap.Get;
import com.perosa.bot.traffic.http.client.wrap.Post;

public interface Forwarder {

    ForwarderResponse get(Get input) throws Exception;

    ForwarderResponse post(Post input) throws Exception;

    static Forwarder getInstance() {
        return new JavaClientImpl();
    }

}
