package ru.skogmark.framework.request;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;

public class OutgoingRequestService {
    private final HttpClient httpClient;
    private final Executor outgoingRequestExecutor;

    public OutgoingRequestService(@Nonnull HttpClient httpClient, @Nonnull Executor outgoingRequestExecutor) {
        this.httpClient = requireNonNull(httpClient, "httpClient");
        this.outgoingRequestExecutor = requireNonNull(outgoingRequestExecutor, "outgoingRequestExecutor");
    }

    public void execute(HttpHost target, HttpRequest request, Consumer<HttpResponse> onResponseReceivedCallback) {
        outgoingRequestExecutor.execute(() -> {
            try {
                onResponseReceivedCallback.accept(httpClient.execute(target, request));
            } catch (IOException e) {
                throw new IllegalStateException("Exception caught during http request execution", e);
            }
        });
    }
}
