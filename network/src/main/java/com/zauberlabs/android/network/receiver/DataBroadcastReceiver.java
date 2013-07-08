package com.zauberlabs.android.network.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import com.google.common.base.Preconditions;
import com.zauberlabs.android.network.BroadcastManager;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by hernan on 5/7/13.
 */
public class DataBroadcastReceiver<T extends Parcelable> extends BroadcastReceiver implements EventReceiver {

    private Command<T> command;
    private Event event;

    public DataBroadcastReceiver(Command<T> command, Event event) {
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
        T payload = BroadcastManager.getSingleObjectPayload(intent);
        command.execute(context, payload);
    }

}
