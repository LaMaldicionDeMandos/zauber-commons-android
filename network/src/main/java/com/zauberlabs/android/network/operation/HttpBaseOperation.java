package com.zauberlabs.android.network.operation;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpStatusCodes;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.gson.GsonFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by hernan on 7/11/13.
 */
public class HttpBaseOperation implements HttpOperation {

    protected final URL baseUrl;
    private final String method;
    private Object payload;
    private Map<? extends String, ?> params;

    public HttpBaseOperation(String baseUrl, String method) throws MalformedURLException {
        checkArgument(method != null && method.length() > 0, "Http Method must be non empty");
        this.baseUrl = new URL(baseUrl);
        this.method = method;
        this.params = null;
    }

    public HttpBaseOperation(String baseUrl, String method, Map<? extends String, ?> params) throws MalformedURLException {
        this(baseUrl, method);
        this.params = params;
    }

    public HttpBaseOperation(String baseUrl, String method, Object payload) throws MalformedURLException {
        this(baseUrl, method);
        this.payload = payload;
    }

    @Override
    public HttpRequest buildRequest(String path, HttpRequestFactory factory) throws IOException {
        GenericUrl url = buildGenericURL(path);
        GsonFactory gsonFactory = new GsonFactory();
        HttpContent content = null;
        if (payload != null) {
            content = new JsonHttpContent(gsonFactory, payload);
        }
        HttpRequest request = factory.buildRequest(method, url, content);
        request.setParser(gsonFactory.createJsonObjectParser());
        return request;
    }

    protected GenericUrl buildGenericURL(String path) throws MalformedURLException {
        URL fullUrl = new URL(baseUrl, path);
        GenericUrl url = new GenericUrl(fullUrl);
        if (params != null) {
            url.putAll(params);
        }
        return url;
    }

    @Override
    public boolean isSuccessful(HttpResponse response) {
        return HttpStatusCodes.isSuccess(response.getStatusCode());
    }
}
