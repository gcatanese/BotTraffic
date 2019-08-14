package com.perosa.bot.traffic.core.common;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

public class CoreConfigurationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoreConfigurationTest.class);

    @Test
    void getHome() {
        assertEquals("src/test/resources/", new CoreConfiguration().getHome());
    }


}
