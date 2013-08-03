package com.zauberlabs.android.network.request;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.zauberlabs.android.network.BroadcastManager;
import com.zauberlabs.android.network.receiver.BasicEvent;
import com.zauberlabs.android.network.receiver.Event;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by hernan on 7/9/13.
 */
@RunWith(MockitoJUnitRunner.class)
public class BaseRequestListenerTest {

    @Mock
    private BroadcastManager broadcastManager;
    @Mock
    private SpiceException exception;
    @Mock
    private Event payload;

    private RequestListener listener;

    @Before
    public void setUp() {
        listener = new BaseRequestListener(broadcastManager) {
            @Override
            protected void handleSuccess(Object payload) {
                ((Event)payload).getName();
            }
        };
    }

    @Test
    public void shouldBuildBaseRequestWithBroadcastManager() {
        assertNotNull(new BaseRequestListener<String>(broadcastManager) {
            @Override
            protected void handleSuccess(String payload) {
            }
        });
    }

    @Test
    public void shouldSendErrorBroadcastOnFailure() {
        listener.onRequestFailure(exception);
        verify(broadcastManager).broadcast(eq(BasicEvent.ERROR), eq(exception));
    }

    @Test
    public void shouldCallDelegateMethodOnSuccess() {
        listener.onRequestSuccess(payload);
        verify(payload).getName();
    }
}
