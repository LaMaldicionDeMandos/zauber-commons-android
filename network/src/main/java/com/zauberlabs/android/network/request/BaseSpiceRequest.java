package com.zauberlabs.android.network.request;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.octo.android.robospice.request.googlehttpclient.GoogleHttpClientSpiceRequest;

import java.net.URL;

/**
 * Created by hernan on 8/7/13.
 */
public abstract class BaseSpiceRequest<T> extends GoogleHttpClientSpiceRequest<T> {

    private final HttpRequestBuilder requestBuilder;

    public BaseSpiceRequest(Class<T> clazz, HttpRequestBuilder requestBuilder) {
        super(clazz);
        this.requestBuilder = requestBuilder;
    }

    @Override
    public T loadDataFromNetwork() throws Exception {
        final HttpRequest request = requestBuilder.build(getResourcePath());
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
