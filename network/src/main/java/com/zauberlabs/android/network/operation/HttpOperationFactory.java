package com.zauberlabs.android.network.operation;

import com.google.api.client.http.HttpMethods;

import java.net.MalformedURLException;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by hernan on 7/12/13.
 */
public class HttpOperationFactory {

    private final String baseUrl;

    public HttpOperationFactory(String baseUrl) {
        checkArgument(baseUrl != null && baseUrl.length() > 0, "baseUrl should be not empty");
        this.baseUrl = baseUrl;
    }

    public HttpOperation buildGetWithHeader(Map<? extends String, ?> headerValues) throws MalformedURLException {
        return buildGet(null, headerValues);
    }

    public HttpOperation buildGet() throws MalformedURLException {
        return buildGet(null);
    }

    public HttpOperation buildGet(Map<? extends String, ?> parameters) throws MalformedURLException {
        return new HttpBaseOperation(baseUrl, HttpMethods.GET, parameters);
    }

    public HttpOperation buildGet(Map<? extends String, ?> parameters, Map<? extends String, ?> headerValues) throws MalformedURLException {
        HttpOperation operation = buildGet(parameters);
        operation.setHeaders(headerValues);
        return operation;
    }

    public HttpOperation buildPost(Object payload) throws MalformedURLException {
        return new HttpBaseOperation(baseUrl, HttpMethods.POST, payload);
    }

    public HttpOperation buildPost(Object payload, Map<? extends String, ?> headerValues) throws MalformedURLException {
        HttpOperation operation = buildPost(payload);
        operation.setHeaders(headerValues);
        return operation;
    }

    public HttpOperation buildPut(Object payload) throws MalformedURLException {
        return new HttpBaseOperation(baseUrl, HttpMethods.PUT, payload);
    }

    public HttpOperation buildPut(Object payload, Map<? extends String, ?> headerValues) throws MalformedURLException {
        HttpOperation operation = buildPut(payload);
        operation.setHeaders(headerValues);
        return operation;
    }

    public HttpOperation buildDelete() throws MalformedURLException {
        return new HttpBaseOperation(baseUrl, HttpMethods.DELETE);
    }

    public HttpOperation buildDelete(Map<? extends String, ?> headerValues) throws MalformedURLException {
        HttpOperation operation = buildDelete();
        operation.setHeaders(headerValues);
        return operation;
    }

}
