package ru.skogmark.common.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * Simple json data serializer
 *
 * @author svip
 * 2017-07-27
 */
public class JacksonObjectMapperSerializerAdapter implements Serializer {
    private final ObjectMapper objectMapper = new ObjectMapper().setSerializationInclusion(NON_NULL);

    @Override
    public String serialize(Object data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Unable to serialize object " + data, e);
        }
    }

    @Override
    public <T> T deserialize(String data, Class<T> resultType) {
        try {
            return objectMapper.readValue(data, resultType);
        } catch (IOException e) {
            throw new SerializationException(
                    "Failure to deserialize data to object type " + resultType + ". Data: " + data, e);
        }
    }
}
