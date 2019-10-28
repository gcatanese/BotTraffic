package com.perosa.bot.traffic.core.common;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

public class EnvConfigurationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnvConfigurationTest.class);

    @Test
    void getPort() {
        assertEquals(8886, new EnvConfiguration().getPort());
    }

    @Test
    void getHome() {
        assertEquals("src/test/resources/", new EnvConfiguration().getHome());
    }


}
