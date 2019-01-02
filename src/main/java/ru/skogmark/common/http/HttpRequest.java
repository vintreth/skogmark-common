package ru.skogmark.common.http;

/**
 * Http request interface
 */
public interface HttpRequest {
    /**
     * Makes request and returns result as a string
     *
     * @param httpRequestHeader request header
     * @param body              request body
     * @return result as a string
     */
    String makeRequest(HttpRequestHeader httpRequestHeader, Object body);

    /**
     * Makes request and return result as object
     *
     * @param httpRequestHeader request header
     * @param body              request body
     * @param resultType        class of result object
     * @param <T>               type of result object
     * @return result object
     */
    <T> T makeRequest(HttpRequestHeader httpRequestHeader, Object body, Class<T> resultType);
}
