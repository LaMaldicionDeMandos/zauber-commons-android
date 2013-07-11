package com.zauberlabs.android.network.operation;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.gson.GsonFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by hernan on 7/11/13.
 */
public class HttpGetOperation implements HttpOperation {

    private final URL baseUrl;

    public HttpGetOperation(String baseUrl) throws MalformedURLException {
        this.baseUrl = new URL(baseUrl);
    }

    @Override
    public HttpRequest buildRequest(String path, HttpRequestFactory factory) throws IOException {
        URL url = new URL(baseUrl, path);
        HttpRequest request = factory.buildGetRequest(new GenericUrl(url));
        request.setParser(new GsonFactory().createJsonObjectParser());
        return request;
    }

    @Override
    public boolean isSuccessful(HttpResponse response) {
        return false;
    }
}
