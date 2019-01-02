package ru.skogmark.common.config;

import org.junit.Test;

public class ConfigurationsTest {
    @Test
    public void shouldDeserializeConfigFromProps() {
        Configurations.newPropertiesConfigurationLoader().loadConfiguration(Object.class,
                "test-config-0.properties");
    }
}