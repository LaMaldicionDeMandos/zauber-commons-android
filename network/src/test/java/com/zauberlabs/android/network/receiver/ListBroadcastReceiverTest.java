package com.zauberlabs.android.network.receiver;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by hernan on 7/5/13.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class ListBroadcastReceiverTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private ListCommand<Parcelable> command;
    private Event event;
    private Intent intent;
    private Context context;
    private ArrayList<Parcelable> payload;

    private ListBroadcastReceiver receiver;

    @Before
    public void setUp() throws Exception {
        command = mock(ListCommand.class);
        event = mock(Event.class);
        intent = mock(Intent.class);
        context = mock(Context.class);
        payload = mock(ArrayList.class);
        receiver = new ListBroadcastReceiver(command, event);
    }

    @Test
    public void shouldBuildReceiver() {
        ListBroadcastReceiver receiver = new ListBroadcastReceiver(command, event);
        assertNotNull(receiver);
    }

    @Test
    public void shouldFailWhenCommandIsNull() {
        expectedException.expect(NullPointerException.class);
        ListBroadcastReceiver receiver = new ListBroadcastReceiver(null, event);
        assertNull(receiver);
    }

    @Test
    public void shouldFailWhenEventIsNull() {
        expectedException.expect(NullPointerException.class);
        ListBroadcastReceiver receiver = new ListBroadcastReceiver(command, null);
        assertNull(receiver);
    }

    @Test
    public void shouldReturnEvent() {
        assertEquals(event, receiver.getEvent());
    }

    @Test
    public void shouldObtainListFromIntentOnReceive() {
        receiver.onReceive(context, intent);
        verify(intent).getParcelableArrayListExtra(anyString());
    }

    @Test
    public void shouldCallCommandOnReceive() {
        when(intent.getParcelableArrayListExtra(anyString())).thenReturn(payload);
        receiver.onReceive(context, intent);
        verify(command).execute(eq(context), eq(payload));
    }
}
