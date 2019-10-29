package com.perosa.bot.traffic.core.rule;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OperatorTest {

    @Test
    void equal() {

        Rule rule = new Rule();
        assertTrue(Operator.EQUAL.apply("a", "a"));
    }

    @Test
    void notEqual() {

        Rule rule = new Rule();
        assertTrue(Operator.NOT_EQUAL.apply("a", "b"));
    }

    @Test
    void equalIgnoreCase() {

        Rule rule = new Rule();
        assertTrue(Operator.EQUAL_IGNORE_CASE.apply("A", "a"));
    }

    @Test
    public void contain() {

        Rule rule = new Rule();
        assertTrue(Operator.CONTAIN.apply("Alpha", "ph"));
    }

    @Test
    public void startWith() {

        Rule rule = new Rule();
        assertTrue(Operator.START_WITH.apply("Alpha", "Al"));
    }

    @Test
    public void endWith() {

        Rule rule = new Rule();
        assertTrue(Operator.END_WITH.apply("Alpha", "ha"));
    }

}
