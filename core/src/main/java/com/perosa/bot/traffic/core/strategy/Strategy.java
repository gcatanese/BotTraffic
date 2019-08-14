package com.perosa.bot.traffic.core.strategy;

import com.perosa.bot.traffic.core.service.Consumable;
import com.perosa.bot.traffic.core.service.ConsumableService;

import java.util.List;

public interface Strategy {

    Consumable getTarget(List<ConsumableService> targets);


}
