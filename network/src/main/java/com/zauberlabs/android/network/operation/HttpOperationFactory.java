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

    public HttpOperation buildGet() throws MalformedURLException {
        return buildGet(null);
    }

    public HttpOperation buildGet(Map<? extends String, ?> parameters) throws MalformedURLException {
        return new HttpBaseOperation(baseUrl, HttpMethods.GET, parameters);
    }

    public HttpOperation buildPost(Object payload) throws MalformedURLException {
        return new HttpBaseOperation(baseUrl, HttpMethods.POST, payload);
    }

    public HttpOperation buildPut(Object payload) throws MalformedURLException {
        return new HttpBaseOperation(baseUrl, HttpMethods.PUT, payload);
    }

    public HttpOperation buildDelete() throws MalformedURLException {
        return new HttpBaseOperation(baseUrl, HttpMethods.DELETE);
    }

}
