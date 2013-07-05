package com.zauberlabs.android.network.receiver;

import android.os.Parcelable;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;

/**
 * Created by hernan on 5/7/13.
 */
@RunWith(MockitoJUnitRunner.class)
public class DataBroadcastReceiverTest {

    @Mock
    private Command<Parcelable> command;

    @Mock
    private Event event;

    private DataBroadcastReceiver receiver;

    @Before
    public void setUp() throws Exception {
        receiver = new DataBroadcastReceiver(command, event);
    }

    @Test
    public void shouldBuildReceiverWithCommandAndEvent() {
        DataBroadcastReceiver receiver = new DataBroadcastReceiver(command, event);
        assertNotNull(receiver);
    }
}
