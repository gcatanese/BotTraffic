package com.perosa.bot.traffic.core.strategy;

import com.perosa.bot.traffic.core.service.Consumable;
import com.perosa.bot.traffic.core.service.ConsumableService;

import java.util.List;
import java.util.Random;

public class RandomStrategy implements Strategy {

    public Consumable getTarget(List<ConsumableService> targets) {

        Consumable consumable = null;

        if (targets.size() == 1) {
            consumable = targets.get(0);
        } else {

            int random = new Random().nextInt(targets.size());
            consumable = targets.stream().skip(random - 1).findFirst().get();

        }

        //LOGGER.info("target " + consumable.toString());

        return consumable;
    }

}
