package com.zauberlabs.android.network.operation;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpStatusCodes;
import com.google.api.client.json.gson.GsonFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Created by hernan on 7/11/13.
 */
public class HttpGetOperation implements HttpOperation {

    private final URL baseUrl;
    private final Map<? extends String, ?> params;

    public HttpGetOperation(String baseUrl) throws MalformedURLException {
        this(baseUrl, null);
    }

    public HttpGetOperation(String baseUrl, Map<? extends String, ?> params) throws MalformedURLException {
        this.params = params;
        this.baseUrl = new URL(baseUrl);
    }

    @Override
    public HttpRequest buildRequest(String path, HttpRequestFactory factory) throws IOException {
        GenericUrl url = buildGenericURL(path);
        HttpRequest request = factory.buildGetRequest(url);
        request.setParser(new GsonFactory().createJsonObjectParser());
        return request;
    }

    private GenericUrl buildGenericURL(String path) throws MalformedURLException {
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
