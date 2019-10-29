package com.perosa.bot.traffic.core.strategy;

import com.perosa.bot.traffic.core.service.ConsumableService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WeightedRoundRobinBalancerTest {

    @Test
    void getHostEntries() {
        LocalWeightedRoundRobinBalancer weightedRoundRobinBalancer = new LocalWeightedRoundRobinBalancer();

        assertEquals(45, weightedRoundRobinBalancer.getWeightedTargets(new ConsumableService("01", "localhost", 8080), 45).size());
    }

    @Test
    void getBucket() {

        List<ConsumableService> targets = new ArrayList<>();
        targets.add(new ConsumableService("01", "localhost", 8080, 70));
        targets.add(new ConsumableService("02", "localhost", 8081, 20));
        targets.add(new ConsumableService("02", "localhost", 8082, 10));

        LocalWeightedRoundRobinBalancer weightedRoundRobinBalancer = new LocalWeightedRoundRobinBalancer();

        assertNotNull(weightedRoundRobinBalancer.getBuckets(targets));
        assertEquals(100, weightedRoundRobinBalancer.getBuckets(targets).size());

    }

    @Test
    void getTarget() {

        List<ConsumableService> targets = new ArrayList<>();
        targets.add(new ConsumableService("01", "localhost", 8080, 70));
        targets.add(new ConsumableService("02", "localhost", 8081, 20));
        targets.add(new ConsumableService("02", "localhost", 8082, 10));

        LocalWeightedRoundRobinBalancer weightedRoundRobinBalancer = new LocalWeightedRoundRobinBalancer();

        assertEquals("01", weightedRoundRobinBalancer.getTarget(targets).getId());

    }


    class LocalWeightedRoundRobinBalancer extends WeightedRoundRobinBalancer {
        @Override
        int getRandomEntry() {
            return 45;
        }
    }

}