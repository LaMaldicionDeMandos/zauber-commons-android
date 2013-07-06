package com.zauberlabs.android.network.receiver;

import android.content.Context;
import android.content.Intent;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Created by hernan on 7/6/13.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class VoidBroadcastReceiverTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private VoidCommand command;
    private Event event;
    private Context context;
    private Intent intent;

    private VoidBroadcastReceiver receiver;

    @Before
    public void setUp() {
        command = mock(VoidCommand.class);
        event = mock(Event.class);
        context = mock(Context.class);
        intent = mock(Intent.class);
        receiver = new VoidBroadcastReceiver(command, event);
    }

    @Test
    public void shouldBuildReceiver() {
        assertNotNull(new VoidBroadcastReceiver(command, event));
    }

    @Test
    public void shouldFailWithNilCommand() {
        expectedException.expect(NullPointerException.class);
        receiver = new VoidBroadcastReceiver(null, event);
        assertNull(receiver);
    }

    @Test
    public void shouldFailWithNilEvent() {
        expectedException.expect(NullPointerException.class);
        receiver = new VoidBroadcastReceiver(command, null);
        assertNull(receiver);
    }

    @Test
    public void shouldDoNothingWithTheIntentOnReceive() {
        receiver.onReceive(context, intent);
        verifyZeroInteractions(intent);
    }

    @Test
    public void shouldReturnEvent() {
        assertEquals(event, receiver.getEvent());
    }

    @Test
    public void shouldCallCommandOnReceive() {
        receiver.onReceive(context, intent);
        verify(command).execute(eq(context));
    }
}
