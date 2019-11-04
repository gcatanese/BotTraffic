package com.perosa.bot.traffic.http.client;

import com.perosa.bot.traffic.http.client.wrap.Get;
import com.perosa.bot.traffic.http.client.wrap.Post;
import com.perosa.bot.traffic.http.server.dispatch.workflow.Router;

public class SingleInstanceTest {

    //@Test
    public void post() throws Exception {
        Router _instance = new Router();

        Post post = new Post("http://localhost:8383", "/svc1/");
        _instance.post(post);
    }

    //@Test
    public void get() throws Exception {
        Router _instance = new Router();

        Get get = new Get("http://localhost:8383", "/svc1/");

        _instance.get(get);
    }

}
