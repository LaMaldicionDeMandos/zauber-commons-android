package com.zauberlabs.android.network.request;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.gson.Gson;
import com.octo.android.robospice.request.googlehttpclient.GoogleHttpClientSpiceRequest;
import com.zauberlabs.android.network.operation.HttpOperation;

/**
 * Created by hernan on 8/7/13.
 */
public abstract class BaseSpiceRequest<T> extends GoogleHttpClientSpiceRequest<T> {

    private final HttpOperation operation;
    private final static Gson gson = new Gson();

    public BaseSpiceRequest(Class<T> clazz, HttpOperation operation) {
        super(clazz);
        this.operation = operation;
    }

    @Override
    public T loadDataFromNetwork() throws Exception {
        final HttpRequest request = operation.buildRequest(getResourcePath(), getHttpRequestFactory());
        HttpResponse response = request.execute();
        if (!operation.isSuccessful(response)) {
            throw new HttpResponseException(response);
        }
        T value = null;
        if (getResultType() != Void.class) {
            String json = response.parseAsString();
            value = gson.fromJson(json, getResultType());
        }
        return value;
    }

    protected abstract String getResourcePath();
}
