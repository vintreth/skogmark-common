package ru.skogmark.common.config;

/**
 * @author svip
 * 2016-12-17
 */
public class ConfigurationLoadingException extends RuntimeException {
    public ConfigurationLoadingException(String message) {
        super(message);
    }

    public ConfigurationLoadingException(String message, Throwable cause) {
        super(message, cause);
    }
}
