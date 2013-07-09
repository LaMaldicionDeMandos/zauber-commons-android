package com.zauberlabs.android.network.request;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.zauberlabs.android.network.BroadcastManager;
import com.zauberlabs.android.network.receiver.BasicEvent;

/**
 * Created by hernan on 7/9/13.
 */
public abstract class BaseRequestListener<T> implements RequestListener<T> {

    protected final BroadcastManager broadcastManager;

    public BaseRequestListener(BroadcastManager broadcastManager) {
        this.broadcastManager = broadcastManager;
    }

    @Override
    public void onRequestFailure(SpiceException e) {
        broadcastManager.broadcast(BasicEvent.ERROR, e);
    }

    @Override
    public void onRequestSuccess(T payload) {
        handleSuccess(payload);
    }

    protected abstract void handleSuccess(T payload);
}
