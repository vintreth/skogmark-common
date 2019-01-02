package ru.skogmark.common.config;

import java.io.InputStream;

interface ConfigurationSerializer {
    <T> T deserialize(Class<T> configClass, InputStream configFileInputStream);
}
