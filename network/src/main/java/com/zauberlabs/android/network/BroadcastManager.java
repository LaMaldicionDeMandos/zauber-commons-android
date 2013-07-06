package com.zauberlabs.android.network;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;

import com.zauberlabs.android.network.receiver.Event;
import com.zauberlabs.android.network.receiver.EventReceiver;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by hernan on 7/5/13.
 */
public class BroadcastManager {

    public static final String SINGLE_OBJECT_PAYLOAD_KEY = "DATA";
    public static final String LIST_PAYLOAD_KEY = "LIST";

    private final LocalBroadcastManager broadcast;

    public BroadcastManager(LocalBroadcastManager broadcast) {
        this.broadcast = broadcast;
    }

    public <T extends BroadcastReceiver & EventReceiver> void register(T receiver) {
        broadcast.registerReceiver(receiver, new IntentFilter(receiver.getEvent().getName()));
    }

    public <T extends BroadcastReceiver> void unregister(T receiver) {
        broadcast.unregisterReceiver(receiver);
    }

    public void broadcast(Event event) {
        Intent intent = buildIntent(event);
        broadcast.sendBroadcast(intent);
    }

    public void broadcast(Event event, String payload) {
        Intent intent = buildIntent(event);
        intent.putExtra(SINGLE_OBJECT_PAYLOAD_KEY, payload);
        broadcast.sendBroadcast(intent);
    }

    public <T extends Parcelable> void broadcast(Event event, T payload) {
        Intent intent = buildIntent(event);
        intent.putExtra(SINGLE_OBJECT_PAYLOAD_KEY, payload);
        broadcast.sendBroadcast(intent);
    }

    public <T extends Parcelable> void broadcast(Event event, ArrayList<T> payload) {
        Intent intent = buildIntent(event);
        intent.putParcelableArrayListExtra(LIST_PAYLOAD_KEY, payload);
        broadcast.sendBroadcast(intent);
    }

    public static <T extends Parcelable> T getSingleObjectPayload(Intent intent) {
        return intent.getParcelableExtra(SINGLE_OBJECT_PAYLOAD_KEY);
    }

    public static <T extends  Parcelable>List<T> getListPayload(Intent intent) {
        return intent.getParcelableArrayListExtra(LIST_PAYLOAD_KEY);
    }

    private Intent buildIntent(Event event) {
        checkArgument(event != null, "Must broadcast an event");
        checkArgument(event.getName() != null, "Event must have a name");
        return new Intent(event.getName());
    }

}
