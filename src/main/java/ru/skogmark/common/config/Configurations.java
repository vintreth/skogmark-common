package ru.skogmark.common.config;

import javax.annotation.Nonnull;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;

public final class Configurations {
    private Configurations() {
    }

    public static ConfigurationLoader newXmlConfigurationLoader() {
        return new ConfigurationLoader(new XmlConfigurationSerializer());
    }

    public static ConfigurationLoader newXmlConfigurationLoader(@Nonnull String executionPath) {
        return new ConfigurationLoader(executionPath, new XmlConfigurationSerializer());
    }

    private static class XmlConfigurationSerializer implements ConfigurationSerializer {
        @SuppressWarnings("unchecked")
        public <T> T deserialize(Class<T> configClass, InputStream configFileInputStream) {
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(configClass);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                return (T) unmarshaller.unmarshal(configFileInputStream);
            } catch (JAXBException e) {
                throw new ConfigurationLoadingException("Failure to load configuration for " + configClass, e);
            }
        }
    }
}
