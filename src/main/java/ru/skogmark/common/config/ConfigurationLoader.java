package ru.skogmark.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.*;

import static java.util.Objects.requireNonNull;

/**
 * @author svip
 * 2016-12-17
 */
public class ConfigurationLoader {
    private static final String EXTERNAL_CONFIG_DIRECTORY_PATH = "/../conf";

    private static final Logger log = LoggerFactory.getLogger(ConfigurationLoader.class);

    private final String executionPath;
    private final ConfigurationSerializer configurationSerializer;

    ConfigurationLoader(@Nonnull ConfigurationSerializer configurationSerializer) {
        this(System.getProperty("user.dir"), configurationSerializer);
    }

    ConfigurationLoader(@Nonnull String executionPath,
                        @Nonnull ConfigurationSerializer configurationSerializer) {
        this.executionPath = requireNonNull(executionPath, "executionPath");
        this.configurationSerializer = requireNonNull(configurationSerializer, "configurationSerializer");
    }

    public <T> T loadConfiguration(Class<T> configClass, String configFileName) {
        log.debug("Loading configuration for " + configClass);
        try {
            try (InputStream configFileInputStream = getConfigFileInputStream(configFileName)) {
                return configurationSerializer.deserialize(configClass, configFileInputStream);
            }
        } catch (IOException e) {
            throw new ConfigurationLoadingException(
                    "Failure to close input stream for config file " + configFileName, e);
        }
    }

    private InputStream getConfigFileInputStream(String configFileName) {
        File externalConfigFile = new File(getConfigLocation() + "/" + configFileName);
        try {
            if (externalConfigFile.exists() && externalConfigFile.isFile()) {
                log.debug("Loading config from external directory");
                return new FileInputStream(externalConfigFile);
            } else {
                log.debug("Loading config from resources");
                return getClass().getResourceAsStream(configFileName);
            }
        } catch (FileNotFoundException e) {
            throw new ConfigurationLoadingException("Unable to resolve file " + configFileName, e);
        }
    }

    private String getConfigLocation() {
        String configLocation;
        if (null != (configLocation = System.getProperty("app.configLocation"))) {
            return configLocation;
        }
        return executionPath + EXTERNAL_CONFIG_DIRECTORY_PATH;
    }
}
