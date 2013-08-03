package com.zauberlabs.android.network.operation;

import com.google.api.client.http.HttpMethods;
import com.google.common.collect.Maps;

import java.net.MalformedURLException;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by hernan on 7/12/13.
 */
public class HttpOperationFactory {

    private final String baseUrl;
    private final Map<? extends String, ?> headers;

    public HttpOperationFactory(String baseUrl) {
        this(baseUrl, Maps.<String, Object>newHashMap());
    }

    public HttpOperationFactory(String baseUrl, Map<? extends String, ?> headers) {
        checkArgument(baseUrl != null && baseUrl.length() > 0, "baseUrl should be not empty");
        this.headers = checkNotNull(headers);
        this.baseUrl = baseUrl;
    }

    private <T> HttpOperation build(String method, T attrs) throws MalformedURLException {
        HttpOperation operation = new HttpBaseOperation(baseUrl, method, attrs);
        operation.addHeaders(headers);
        return operation;
    }

    public HttpOperation buildGet() throws MalformedURLException {
        return buildGet(null);
    }

    public HttpOperation buildGet(Map<? extends String, ?> parameters) throws MalformedURLException {
        return build(HttpMethods.GET, parameters);
    }

    public HttpOperation buildPost(Object payload) throws MalformedURLException {
        return build(HttpMethods.POST, payload);
    }

    public HttpOperation buildPut(Object payload) throws MalformedURLException {
        return build(HttpMethods.PUT, payload);
    }

    public HttpOperation buildDelete() throws MalformedURLException {
        return build(HttpMethods.DELETE, null);
    }
}
