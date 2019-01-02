package ru.skogmark.common.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Generic implementation of http request
 *
 * @author svip
 *         2016-12-24
 */
public class SerializerAwareHttpRequest implements HttpRequest {
    private static final Logger log = LoggerFactory.getLogger(SerializerAwareHttpRequest.class);

    private final Serializer serializer;

    public SerializerAwareHttpRequest(Serializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public String makeRequest(HttpRequestHeader header, Object body) {
        try {
            log.debug("Sending request " + header.getHttpMethod() + " " + header.getUrl());
            URL url = new URL(header.getUrl());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(header.getHttpMethod().name());
            connection.setRequestProperty("User-Agent", header.getUserAgent());
            connection.setRequestProperty("Content-Type", header.getContentType());
            if (null != body && HttpMethod.GET != header.getHttpMethod()) {
                String bodyString = serializer.serialize(body);
                connection.setDoOutput(true);
                try (OutputStreamWriter writer = new OutputStreamWriter(
                    connection.getOutputStream(), header.getDefaultCharset())) {
                    writer.write(bodyString);
                    writer.flush();
                }
            }
            //todo process response code advanced
            int responseCode = connection.getResponseCode();
            if (299 < responseCode) {
                throw new HttpException(
                    responseCode,
                    "Unexpected response code " + responseCode + " for request " + header.getHttpMethod()
                        + " " + header.getUrl(),
                    getContent(connection, header));
            }
            return getContent(connection, header);
        } catch (IOException e) {
            throw new HttpException("Failure to send request " + header.getHttpMethod() + " " + header.getUrl(), e);
        }
    }

    @Override
    public <T> T makeRequest(HttpRequestHeader httpRequestHeader, Object body, Class<T> resultType) {
        return serializer.deserialize(makeRequest(httpRequestHeader, body), resultType);
    }

    private String getContent(HttpURLConnection connection, HttpRequestHeader header) {
        log.debug("Retrieving content from http connection");
        try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(connection.getInputStream(), header.getDefaultCharset()))) {
            String line;
            StringBuilder builder = new StringBuilder();
            while (null != (line = reader.readLine())) {
                builder.append(line);
            }
            log.debug("Content has been retrieved: " + builder);
            return builder.toString();
        } catch (IOException e) {
            throw new HttpException("Exception caught while retrieving content from http connection", e);
        }
    }
}
