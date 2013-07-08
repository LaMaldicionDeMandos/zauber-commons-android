package com.zauberlabs.android.network.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import com.zauberlabs.android.network.BroadcastManager;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by hernan on 7/5/13.
 */
public class ListBroadcastReceiver<T extends Parcelable> extends BroadcastReceiver implements EventReceiver {

    private final ListCommand<T> command;
    private final Event event;

    public ListBroadcastReceiver(ListCommand<T> command, Event event) {
        checkNotNull(command);
        checkNotNull(event);
        this.command = command;
        this.event = event;
    }

    @Override
    public Event getEvent() {
        return event;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        List<T> payload = BroadcastManager.getListPayload(intent);
        command.execute(context, payload);
    }
}
