package com.perosa.bot.traffic.http.client;

import com.perosa.bot.traffic.http.client.wrap.Get;
import com.perosa.bot.traffic.http.client.wrap.Post;

public interface RoutingClient {

    RoutingClientResponse get(Get input) throws Exception;

    RoutingClientResponse post(Post input) throws Exception;

}
