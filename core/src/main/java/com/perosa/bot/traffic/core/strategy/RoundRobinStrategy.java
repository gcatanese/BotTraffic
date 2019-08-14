package com.perosa.bot.traffic.core.strategy;

import com.perosa.bot.traffic.core.service.Consumable;
import com.perosa.bot.traffic.core.service.ConsumableService;

import java.util.List;

public class RoundRobinStrategy implements Strategy {

    private static int next = 0;

    public Consumable getTarget(List<ConsumableService> targets) {

        Consumable consumable = getNext(targets);

        return consumable;
    }

    private Consumable getNext(List<ConsumableService> targets) {
        Consumable consumable = targets.get(next);
        next++;

        if(next >= targets.size()) {
            next = 0;
        }

        return consumable;
    }

}
