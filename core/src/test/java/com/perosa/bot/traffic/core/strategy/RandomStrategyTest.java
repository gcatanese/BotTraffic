package com.perosa.bot.traffic.core.strategy;

import com.perosa.bot.traffic.core.service.ConsumableService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RandomStrategyTest {

    private RandomStrategy randomStrategy = new RandomStrategy();

    @Test
    void getTarget() {

        List<ConsumableService> targets = new ArrayList<>();
        targets.add(new ConsumableService("01", "localhost", 8080));
        targets.add(new ConsumableService("02", "localhost", 8081));

        assertNotNull(randomStrategy.getTarget(targets));
    }

    @Test
    void getWhenSingleTarget() {

        List<ConsumableService> targets = new ArrayList<>();
        targets.add(new ConsumableService("01", "localhost", 8080));

        assertEquals("01", randomStrategy.getTarget(targets).getId());
    }

}