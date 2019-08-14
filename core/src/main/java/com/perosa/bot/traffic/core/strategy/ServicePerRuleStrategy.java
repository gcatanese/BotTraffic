package com.perosa.bot.traffic.core.strategy;

import com.perosa.bot.traffic.core.service.Consumable;
import com.perosa.bot.traffic.core.service.ConsumableService;

import java.util.List;

public class ServicePerRuleStrategy implements Strategy {

    public Consumable getTarget(List<ConsumableService> targets) {

        Consumable consumable = null;

        if(targets !=null) {
            consumable = targets.get(0);
        }

        return consumable;
    }

}
