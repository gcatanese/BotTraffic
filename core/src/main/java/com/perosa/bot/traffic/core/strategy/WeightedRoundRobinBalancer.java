package com.perosa.bot.traffic.core.strategy;

import com.perosa.bot.traffic.core.service.Consumable;
import com.perosa.bot.traffic.core.service.ConsumableService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WeightedRoundRobinBalancer implements Strategy {

    private static int next = 0;

    public Consumable getTarget(List<ConsumableService> targets) {

        Consumable consumable = null;

        consumable = getBuckets(targets).stream()
                .skip(getRandonEntry())
                .findFirst()
                .get();

        return consumable;
    }

    int getRandonEntry() {
        return new Random().nextInt(101);

    }

    List<ConsumableService> getBuckets(List<ConsumableService> targets) {
        List<ConsumableService> buckets = new ArrayList<>();

        targets.stream().forEach(target -> buckets.addAll(getWeightedTarget(target, target.getWeight())));

        return buckets;
    }


    List<ConsumableService> getWeightedTarget(ConsumableService target, int weight) {
        List<ConsumableService> list = new ArrayList<>();

        for (int i = 0; i < weight; i++) {
            list.add(target);
        }

        return list;
    }

}
