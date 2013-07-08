package com.zauberlabs.android.network;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;

import com.zauberlabs.android.network.receiver.Event;
import com.zauberlabs.android.network.receiver.VoidBroadcastReceiver;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by hernan on 7/5/13.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class BroadcastManagerTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private static final String STRING_PAYLOAD = "PAYLOAD";
    private static final String EVENT_NAME = "A EVENT";

    private Intent intent;
    private Parcelable payload;
    private ArrayList<Parcelable> listPayload;
    private LocalBroadcastManager broadcast;
    private Event event;
    private VoidBroadcastReceiver broadcastReceiver;

    private BroadcastManager manager;

    @Before
    public void setUp() {
        intent = mock(Intent.class);
        payload = mock(Parcelable.class);
        listPayload = mock(ArrayList.class);
        broadcast = mock(LocalBroadcastManager.class);
        event = mock(Event.class);
        broadcastReceiver = mock(VoidBroadcastReceiver.class);
        when(event.getName()).thenReturn(EVENT_NAME);
        when(broadcastReceiver.getEvent()).thenReturn(event);
        manager = new BroadcastManager(broadcast);
    }

    @Test
    public void shouldReturnSingleObject() {
        when(intent.getParcelableExtra(eq(BroadcastManager.SINGLE_OBJECT_PAYLOAD_KEY))).thenReturn(payload);
        assertEquals(payload, BroadcastManager.getSingleObjectPayload(intent));
    }

    @Test
    public void shouldReturnNullWhenNoObjectIsFoundWithKey() {
        when(intent.getParcelableExtra(anyString())).thenReturn(null);
        assertNull(BroadcastManager.getSingleObjectPayload(intent));
    }

    @Test
    public void shouldReturnList() {
        when(intent.getParcelableArrayListExtra(eq(BroadcastManager.LIST_PAYLOAD_KEY))).thenReturn(listPayload);
        assertEquals(listPayload, BroadcastManager.getListPayload(intent));
    }

    @Test
    public void shouldReturnNullWhenNoListIsFoundWithKey() {
        when(intent.getParcelableArrayListExtra(anyString())).thenReturn(null);
        assertNull(BroadcastManager.getListPayload(intent));
    }

    @Test
    public void shouldCreateManager() {
        assertNotNull(new BroadcastManager(broadcast));
    }

    @Test
    public void shouldBroadcastEventWithoutPayload() {
        ArgumentCaptor<Intent> captor = ArgumentCaptor.forClass(Intent.class);
        manager.broadcast(event);
        verify(event, atLeastOnce()).getName();
        verify(broadcast).sendBroadcast(captor.capture());
        Intent intent = captor.getValue();
        assertNotNull(intent);
        assertEquals(EVENT_NAME, intent.getAction());
    }

    @Test
    public void shouldFailToBroadcastNullEvent() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Must broadcast an event");
        manager.broadcast(null);
    }

    @Test
    public void shouldFailToBroadcastEventWithNullName() {
        when(event.getName()).thenReturn(null);
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Event must have a name");
        manager.broadcast(event);
    }

    @Test
    public void shouldBroadcastEventWithStringPayload() {
        ArgumentCaptor<Intent> captor = ArgumentCaptor.forClass(Intent.class);
        manager.broadcast(event, STRING_PAYLOAD);
        verify(event, atLeastOnce()).getName();
        verify(broadcast).sendBroadcast(captor.capture());
        Intent intent = captor.getValue();
        assertNotNull(intent);
        assertEquals(EVENT_NAME, intent.getAction());
        assertEquals(STRING_PAYLOAD, intent.getStringExtra(BroadcastManager.SINGLE_OBJECT_PAYLOAD_KEY));
    }

    @Test
    public void shouldFailToBroadcastNullEventWithString() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Must broadcast an event");
        manager.broadcast(null, STRING_PAYLOAD);
    }

    @Test
    public void shouldFailToBroadcastEventWithNullNameAndString() {
        when(event.getName()).thenReturn(null);
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Event must have a name");
        manager.broadcast(event, STRING_PAYLOAD);
    }

    @Test
    public void shouldBroadcastEventWithParcelablePayload() {
        ArgumentCaptor<Intent> captor = ArgumentCaptor.forClass(Intent.class);
        manager.broadcast(event, payload);
        verify(event, atLeastOnce()).getName();
        verify(broadcast).sendBroadcast(captor.capture());
        Intent intent = captor.getValue();
        assertNotNull(intent);
        assertEquals(EVENT_NAME, intent.getAction());
    }

    @Test
    public void shouldFailToBroadcastNullEventWithParcelable() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Must broadcast an event");
        manager.broadcast(null, payload);
    }

    @Test
    public void shouldFailToBroadcastEventWithNullNameAndParcelable() {
        when(event.getName()).thenReturn(null);
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Event must have a name");
        manager.broadcast(event, payload);
    }

    @Test
    public void shouldBroadcastEventWithParcelableListPayload() {
        ArgumentCaptor<Intent> captor = ArgumentCaptor.forClass(Intent.class);
        manager.broadcast(event, listPayload);
        verify(event, atLeastOnce()).getName();
        verify(broadcast).sendBroadcast(captor.capture());
        Intent intent = captor.getValue();
        assertNotNull(intent);
        assertEquals(EVENT_NAME, intent.getAction());
    }

    @Test
    public void shouldFailToBroadcastNullEventWithParcelableList() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Must broadcast an event");
        manager.broadcast(null, listPayload);
    }

    @Test
    public void shouldFailToBroadcastEventWithNullNameAndParcelableList() {
        when(event.getName()).thenReturn(null);
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Event must have a name");
        manager.broadcast(event, listPayload);
    }

    @Test
    public void shouldRegisterBroadcastReceiver() {
        ArgumentCaptor<IntentFilter> captor = ArgumentCaptor.forClass(IntentFilter.class);
        manager.register(broadcastReceiver);
        verify(broadcast).registerReceiver(eq(broadcastReceiver), captor.capture());
        IntentFilter filter = captor.getValue();
        assertNotNull(filter);
        assertEquals(EVENT_NAME, filter.getAction(0));
    }

    @Test
    public void shouldUnregisterBroadcastReceiver() {
        manager.unregister(broadcastReceiver);
        verify(broadcast).unregisterReceiver(eq(broadcastReceiver));
    }
}
