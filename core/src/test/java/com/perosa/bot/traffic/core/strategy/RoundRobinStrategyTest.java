package com.perosa.bot.traffic.core.strategy;

import com.perosa.bot.traffic.core.service.ConsumableService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoundRobinStrategyTest {

    @Test
    void getTarget() {

        RoundRobinStrategy roundRobinStrategy = new RoundRobinStrategy();

        List<ConsumableService> targets = new ArrayList<>();
        targets.add(new ConsumableService("01", "localhost", 8080));
        targets.add(new ConsumableService("02", "localhost", 8081));

        assertNotNull(roundRobinStrategy.getTarget(targets));
    }

    @Test
    void getTargetInSequence() {

        RoundRobinStrategy roundRobinStrategy = new RoundRobinStrategy();

        List<ConsumableService> targets = new ArrayList<>();
        targets.add(new ConsumableService("01", "localhost", 8080));
        targets.add(new ConsumableService("02", "localhost", 8081));

        assertEquals("01", roundRobinStrategy.getTarget(targets).getId());
        assertEquals("02", roundRobinStrategy.getTarget(targets).getId());
        assertEquals("01", roundRobinStrategy.getTarget(targets).getId());
    }

}