package com.zauberlabs.android.network.receiver;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by hernan on 5/7/13.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class DataBroadcastReceiverTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private Command<Parcelable> command;
    private Event event;
    private Intent intent;
    private Context context;
    private Parcelable payload;

    private DataBroadcastReceiver receiver;

    @Before
    public void setUp() throws Exception {
        command = mock(Command.class);
        event = mock(Event.class);
        intent = mock(Intent.class);
        context = mock(Context.class);
        payload = mock(Parcelable.class);
        when(intent.getParcelableExtra(anyString())).thenReturn(payload);
        receiver = new DataBroadcastReceiver(command, event);
    }

    @Test
    public void shouldBuildReceiverWithCommandAndEvent() {
        DataBroadcastReceiver receiver = new DataBroadcastReceiver(command, event);
        assertNotNull(receiver);
    }

    @Test
    public void shouldFailWhenCommandIsNull() {
        expectedException.expect(NullPointerException.class);
        DataBroadcastReceiver receiver = new DataBroadcastReceiver(null, event);
        assertNull(receiver);
    }

    @Test
    public void shouldFailWhenEventIsNull() {
        expectedException.expect(NullPointerException.class);
        DataBroadcastReceiver receiver = new DataBroadcastReceiver(command, null);
        assertNull(receiver);
    }

    @Test
    public void shouldReturnEventWhenAsked() {
        assertEquals(event, receiver.getEvent());
    }

    @Test
    public void shouldObtainPayloadFromIntentOnReceive() {
        receiver.onReceive(context, intent);
        verify(intent).getParcelableExtra(anyString());
    }

    @Test
    public void shouldCallCommandWithPayload() {
        receiver.onReceive(context, intent);
        verify(command).execute(eq(context), eq(payload));
    }
}
