package com.perosa.bot.traffic.core.strategy;

import com.perosa.bot.traffic.core.service.Consumable;
import com.perosa.bot.traffic.core.service.ConsumableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;

public class RandomStrategy implements Strategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(RandomStrategy.class);

    public Consumable getTarget(List<ConsumableService> targets) {

        Consumable consumable = null;

        if (targets.size() == 1) {
            consumable = targets.get(0);
        } else {

            int random = new Random().nextInt(targets.size());
            consumable = targets.stream().skip(random).findFirst().get();

        }

        return consumable;
    }

}
