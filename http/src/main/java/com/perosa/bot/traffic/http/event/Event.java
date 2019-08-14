package com.perosa.bot.traffic.http.event;

public class Event {

    private String name;

    public Event(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
