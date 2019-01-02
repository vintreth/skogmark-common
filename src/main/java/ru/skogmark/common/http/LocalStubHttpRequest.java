package ru.skogmark.common.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Local mode stub for http request
 *
 * @author svip
 *         2016-12-24
 */
public class LocalStubHttpRequest implements HttpRequest {
    private static final String LOG_MESSAGE = "Sending request %s %s";

    private static final Logger log = LoggerFactory.getLogger(LocalStubHttpRequest.class);

    @Override
    public String makeRequest(HttpRequestHeader httpRequestHeader, Object body) {
        log.info(String.format(LOG_MESSAGE, httpRequestHeader.getHttpMethod(), httpRequestHeader.getUrl()));
        return "{}";
    }

    @Override
    public <T> T makeRequest(HttpRequestHeader httpRequestHeader, Object body, Class<T> resultType) {
        try {
            log.info(String.format(LOG_MESSAGE, httpRequestHeader.getHttpMethod(), httpRequestHeader.getUrl()));
            return resultType.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new SerializationException("Unable to deserialize stub response", e);
        }
    }
}
