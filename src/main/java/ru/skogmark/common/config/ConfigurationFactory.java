package ru.skogmark.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;

/**
 * @author svip
 *         2016-12-17
 */
public class ConfigurationFactory {
    private static final String EXTERNAL_CONFIG_DIRECTORY_PATH = "/../conf";

    private static final Logger log = LoggerFactory.getLogger(ConfigurationFactory.class);

    private final String executionPath;

    public ConfigurationFactory() {
        this.executionPath = System.getProperty("user.dir");
    }

    public ConfigurationFactory(String executionPath) {
        this.executionPath = executionPath;
    }

    public <T> T loadConfiguration(Class<T> configClass, String configFileName) {
        log.debug("Loading configuration for " + configClass);
        try {
            try (InputStream configFileInputStream = getConfigFileInputStream(configFileName)) {
                return unmarshalConfig(configClass, configFileInputStream);
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
                return Thread.currentThread().getContextClassLoader().getResourceAsStream(configFileName);
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

    @SuppressWarnings("unchecked")
    private <T> T unmarshalConfig(Class<T> configClass, InputStream configFileInputStream) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(configClass);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            return (T) unmarshaller.unmarshal(configFileInputStream);
        } catch (JAXBException e) {
            throw new ConfigurationLoadingException("Failure to load configuration for " + configClass, e);
        }
    }
}
