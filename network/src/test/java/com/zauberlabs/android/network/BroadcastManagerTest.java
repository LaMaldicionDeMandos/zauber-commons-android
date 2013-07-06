package com.zauberlabs.android.network;

import android.content.Intent;
import android.os.Parcelable;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

/**
 * Created by hernan on 7/5/13.
 */
@RunWith(MockitoJUnitRunner.class)
public class BroadcastManagerTest {

    @Mock
    private Intent intent;
    @Mock
    private Parcelable payload;
    @Mock
    private ArrayList<Parcelable> listPayload;

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

}
