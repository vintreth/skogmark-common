package ru.skogmark.common.http;

/**
 * @author svip
 *         2017-07-27
 */
public class SerializationException extends RuntimeException {
    public SerializationException(String message) {
        super(message);
    }

    public SerializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
