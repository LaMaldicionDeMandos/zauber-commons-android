package com.zauberlabs.android.network.request;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.gson.reflect.TypeToken;
import com.octo.android.robospice.request.googlehttpclient.GoogleHttpClientSpiceRequest;
import com.zauberlabs.android.network.operation.HttpOperation;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by hernan on 8/7/13.
 */
public abstract class BaseSpiceRequest<T> extends GoogleHttpClientSpiceRequest<T> {

    private final HttpOperation operation;
    private final Type resultType;

    public BaseSpiceRequest(Class<T> clazz, HttpOperation operation) {
        super(clazz);
        this.operation = operation;
        resultType = null;
    }

    @SuppressWarnings("unchecked")
    public BaseSpiceRequest(TypeToken<T> token, HttpOperation operation) {
        super((Class<T>) token.getRawType());
        this.operation = operation;
        resultType = checkNotNull(token.getType());
    }

    @Override
    @SuppressWarnings("unchecked")
    public T loadDataFromNetwork() throws Exception {
        final HttpRequest request = operation.buildRequest(getResourcePath(), getHttpRequestFactory());
        HttpResponse response = request.execute();
        if (!operation.isSuccessful(response)) {
            throw new HttpResponseException(response);
        }
        T value = null;
        if (getResultType() != Void.class) {
            value = getValue(response);
        }
        return value;
    }

    public boolean isGeneticType() {
        return resultType != null;
    }

    public Type getResultGenericType() {
        return resultType;
    }

    private T getValue(HttpResponse response) throws IOException {
        return isGeneticType()
                ? (T) response.parseAs(getResultGenericType())
                : response.parseAs(getResultType());
    }

    protected abstract String getResourcePath();
}
