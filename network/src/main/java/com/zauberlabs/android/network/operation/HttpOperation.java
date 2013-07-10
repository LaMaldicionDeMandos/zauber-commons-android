package com.zauberlabs.android.network.operation;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;

/**
 * Created by hernan on 8/7/13.
 */
public interface HttpOperation {

    HttpRequest buildRequest(String path);

    boolean isSuccessful(HttpResponse response);
}
