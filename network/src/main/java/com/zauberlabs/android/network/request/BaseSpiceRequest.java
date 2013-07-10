package com.zauberlabs.android.network.request;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.octo.android.robospice.request.googlehttpclient.GoogleHttpClientSpiceRequest;
import com.zauberlabs.android.network.operation.HttpOperation;

/**
 * Created by hernan on 8/7/13.
 */
public abstract class BaseSpiceRequest<T> extends GoogleHttpClientSpiceRequest<T> {

    private final HttpOperation requestBuilder;

    public BaseSpiceRequest(Class<T> clazz, HttpOperation requestBuilder) {
        super(clazz);
        this.requestBuilder = requestBuilder;
    }

    @Override
    public T loadDataFromNetwork() throws Exception {
        final HttpRequest request = requestBuilder.buildRequest(getResourcePath());
        HttpResponse response = request.execute();
        if (!requestBuilder.isSuccessful(response)) {
            throw new HttpResponseException(response);
        }
        T value = null;
        if (getResultType() != Void.class) {
            value = response.parseAs(getResultType());
        }
        return value;
    }

    protected abstract String getResourcePath();
}
