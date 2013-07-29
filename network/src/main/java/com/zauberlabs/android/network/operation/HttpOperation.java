package com.zauberlabs.android.network.operation;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;

import java.io.IOException;
import java.util.Map;

/**
 * Created by hernan on 8/7/13.
 */
public interface HttpOperation {

    HttpRequest buildRequest(String path, HttpRequestFactory httpRequestFactory) throws IOException;

    void addHeaders(Map<? extends String, ?> headerValues);

    <T extends String, K> void addHeader(T name, K value);

    boolean isSuccessful(HttpResponse response);
}
