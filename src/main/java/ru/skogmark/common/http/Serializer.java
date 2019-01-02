package ru.skogmark.common.http;

/**
 * Interface of data serializer
 *
 * @author svip
 *         2017-07-27
 */
public interface Serializer {
    /**
     * Serializes some data object to a string
     *
     * @param data data object
     * @return string data
     */
    String serialize(Object data);

    /**
     * Deserializes data to current object
     *
     * @param resultType class with type of result object
     * @param <T>        type of result object
     * @return result object
     */
    <T> T deserialize(String data, Class<T> resultType);
}
