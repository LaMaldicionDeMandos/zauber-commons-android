package com.zauberlabs.android.network.request;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;

/**
 * Created by hernan on 8/7/13.
 */
public interface HttpRequestBuilder {

    HttpRequest build(String url);

    boolean isSuccessful(HttpResponse response);
}
