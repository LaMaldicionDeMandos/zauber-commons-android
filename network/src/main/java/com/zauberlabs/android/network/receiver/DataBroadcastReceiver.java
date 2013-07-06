package com.zauberlabs.android.network.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import com.zauberlabs.android.network.BroadcastManager;

/**
 * Created by hernan on 5/7/13.
 */
public class DataBroadcastReceiver<T extends Parcelable> extends BroadcastReceiver implements EventReceiver {

    private Command<T> command;
    private Event event;

    public DataBroadcastReceiver(Command<T> command, Event event) {
        this.command = command;
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        T payload = BroadcastManager.getSingleObjectPayload(intent);
        command.execute(context, payload);
    }

}
